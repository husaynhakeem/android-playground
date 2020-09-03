package com.husaynhakeem.biometricsample

import android.annotation.SuppressLint
import android.hardware.biometrics.BiometricManager.Authenticators.*
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyProperties.AUTH_BIOMETRIC_STRONG
import android.security.keystore.KeyProperties.AUTH_DEVICE_CREDENTIAL
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.husaynhakeem.biometricsample.EncryptionMode.DECRYPT
import com.husaynhakeem.biometricsample.EncryptionMode.ENCRYPT
import kotlinx.android.synthetic.main.layout_authenticate.*
import kotlinx.android.synthetic.main.layout_authentication_confirmation.*
import kotlinx.android.synthetic.main.layout_authenticator_types.*
import kotlinx.android.synthetic.main.layout_configuration_change.*
import kotlinx.android.synthetic.main.layout_logging.*
import kotlinx.android.synthetic.main.layout_negative_button.*

// TODO: Remove this suppression
@SuppressLint("NewApi")
class MainActivity : AppCompatActivity() {

    /** Callback to receive callbacks from a authentication operation */
    private lateinit var authenticationCallback: BiometricPrompt.AuthenticationCallback

    /** Manages a biometric prompt, and allows to perform an authentication operation */
    private lateinit var biometricPrompt: BiometricPrompt

    private val encryptionManager by lazy { EncryptionManager(getSecretKeyType()) }
    private lateinit var encryptionMode: EncryptionMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                val type = result.authenticationType
                val cryptoObject = result.cryptoObject
                log("Authentication succeeded [${getAuthenticationType(type)}] - Crypto: $cryptoObject")

                val cipher = cryptoObject?.cipher ?: return
                when (encryptionMode) {
                    ENCRYPT -> log("Encrypted text: ${encryptionManager.encrypt(cipher)}")
                    DECRYPT -> log("Decrypted text: ${encryptionManager.decrypt(cipher)}")
                }
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                log("Authentication error[${getBiometricError(errorCode)}] - $errString")
            }

            override fun onAuthenticationFailed() {
                log("Authentication failed - Biometric is valid but not recognized")
            }
        }

        biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            authenticationCallback
        )

        canAuthenticate.setOnClickListener {
            canAuthenticate()
        }
        authenticate.setOnClickListener {
            authenticateWithoutCrypto()
        }
        authenticateEncrypt.setOnClickListener {
            if (canAuthenticateWithCrypto()) {
                authenticateAndEncrypt()
            }
        }
        authenticateDecrypt.setOnClickListener {
            if (canAuthenticateWithCrypto()) {
                authenticateAndDecrypt()
            }
        }
        clearLogs.setOnClickListener {
            clearLogs()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isChangingConfigurations && !keepAuthenticationDialogOnConfigurationChange()) {
            biometricPrompt.cancelAuthentication()
        }
    }

    private fun showNegativeButton(): Boolean {
        return negativeButton.isChecked
    }

    private fun allowDeviceAuthentication(): Boolean {
        return authenticatorDeviceCredential.isChecked
    }

    private fun showAuthenticationConfirmation(): Boolean {
        return authenticationConfirmation.isChecked
    }

    private fun keepAuthenticationDialogOnConfigurationChange(): Boolean {
        return configurationChange.isChecked
    }

    private fun canAuthenticate() {
        val biometricManager = BiometricManager.from(this)
        val canAuthenticate =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                biometricManager.canAuthenticate(getAuthenticationTypes())
            } else {
                @Suppress("DEPRECATION")
                biometricManager.canAuthenticate()
            }
        log(getBiometricAvailability(canAuthenticate))
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getAuthenticationTypes(): Int {
        var authenticators = 0
        if (authenticatorStrong.isChecked) {
            authenticators = authenticators or BIOMETRIC_STRONG
        }
        if (authenticatorWeak.isChecked) {
            authenticators = authenticators or BIOMETRIC_WEAK
        }
        if (authenticatorDeviceCredential.isChecked) {
            authenticators = authenticators or DEVICE_CREDENTIAL
        }
        return authenticators
    }

    private fun authenticateWithoutCrypto() {
        val promptInfo = buildPromptInfo() ?: return
        biometricPrompt.authenticate(promptInfo)
    }

    private fun canAuthenticateWithCrypto(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            log("Cannot authenticate with crypto on API levels prior to 23 as key-gen is not supported.")
            return false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && (getSecretKeyType() and AUTH_BIOMETRIC_STRONG == 0)) {
            log("Authentication type must be strong to authenticate with crypto.")
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun authenticateAndEncrypt() {
        val promptInfo = buildPromptInfo() ?: return
        val crypto = encryptionManager.getCryptoToEncrypt()
        encryptionMode = ENCRYPT

        try {
            biometricPrompt.authenticate(promptInfo, crypto)
        } catch (exception: IllegalArgumentException) {
            log("Authentication with crypto error - ${exception.message}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun authenticateAndDecrypt() {
        val promptInfo = buildPromptInfo() ?: return
        val crypto = try {
            encryptionManager.getCryptoToDecrypt()
        } catch (exception: IllegalStateException) {
            log("Failed to create a crypto object to use for decryption - ${exception.message}")
            return
        }
        encryptionMode = DECRYPT

        try {
            biometricPrompt.authenticate(promptInfo, crypto)
        } catch (exception: IllegalArgumentException) {
            log("Authentication with crypto error - ${exception.message}")
        }
    }

    /** Build a [PromptInfo] that defines the properties of the biometric prompt dialog. */
    private fun buildPromptInfo(): PromptInfo? {
        val builder = PromptInfo.Builder()
            .setTitle(getString(R.string.prompt_title))
            .setSubtitle(getString(R.string.prompt_subtitle))
            .setDescription(getString(R.string.prompt_description))

        // Show a confirmation button after authentication succeeds
        builder.setConfirmationRequired(showAuthenticationConfirmation())

        // Allow authentication with a password, pin or pattern
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setAllowedAuthenticators(getAuthenticationTypes())
        } else {
            @Suppress("DEPRECATION")
            builder.setDeviceCredentialAllowed(allowDeviceAuthentication())
        }

        // Set a negative button. It would typically display "Cancel"
        if (showNegativeButton()) {
            builder.setNegativeButtonText(getString(R.string.prompt_negative_text))
        }

        return try {
            builder.build()
        } catch (exception: IllegalArgumentException) {
            log("Building prompt info error - ${exception.message}")
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getSecretKeyType(): Int {
        val strong = if (authenticatorStrong.isChecked) AUTH_BIOMETRIC_STRONG else 0
        val deviceCredential =
            if (authenticatorDeviceCredential.isChecked) AUTH_DEVICE_CREDENTIAL else 0
        return strong or deviceCredential
    }

    @SuppressLint("SetTextI18n")
    private fun log(message: String) {
        val currentLogs = logs.text.toString()
        logs.text = "$message\n$currentLogs"
    }

    private fun clearLogs() {
        logs.text = ""
    }
}