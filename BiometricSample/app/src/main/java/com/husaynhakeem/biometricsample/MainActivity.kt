package com.husaynhakeem.biometricsample

import android.annotation.SuppressLint
import android.hardware.biometrics.BiometricManager.Authenticators.*
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProperties.AUTH_BIOMETRIC_STRONG
import android.security.keystore.KeyProperties.AUTH_DEVICE_CREDENTIAL
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.layout_authenticate.*
import kotlinx.android.synthetic.main.layout_authentication_confirmation.*
import kotlinx.android.synthetic.main.layout_authenticator_types.*
import kotlinx.android.synthetic.main.layout_configuration_change.*
import kotlinx.android.synthetic.main.layout_logging.*
import kotlinx.android.synthetic.main.layout_negative_button.*
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {

    /** Callback to receive callbacks from a authentication operation */
    private lateinit var authenticationCallback: BiometricPrompt.AuthenticationCallback

    /** Manages a biometric prompt, and allows to perform an authentication operation */
    private lateinit var biometricPrompt: BiometricPrompt

    private val toEncrypt = "Hello world!"
    private var shouldEncrypt = true
    private var encryptionResult: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                val type = result.authenticationType
                val cryptoObject = result.cryptoObject
                log("Authentication succeeded [${getAuthenticationType(type)}] - Crypto: $cryptoObject")

                if (cryptoObject != null) {
                    val cipher = cryptoObject.cipher ?: return

                    if (shouldEncrypt) { // Should encrypt
                        encryptionResult =
                            cipher.doFinal(toEncrypt.toByteArray(Charset.defaultCharset()))
                        log("Encrypted text: $encryptionResult")
                    } else { // Should decrypt
                        encryptionResult = cipher.doFinal(encryptionResult)
                        log("Decrypted text: $encryptionResult")
                    }

                    shouldEncrypt = !shouldEncrypt
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

        canAuthenticate.setOnClickListener { canAuthenticate() }
        authenticate.setOnClickListener { authenticateWithoutCrypto() }
        authenticateEncrypt.setOnClickListener { tryAuthenticateWithCrypto() }
        authenticateDecrypt.setOnClickListener { tryAuthenticateWithCrypto() }
        clearLogs.setOnClickListener { clearLogs() }
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

    // region Authentication
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

    private fun tryAuthenticateWithCrypto() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            log("Cannot authenticate with crypto on API levels prior to 23 as key-gen is not supported.")
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && (getSecretKeyType() and AUTH_BIOMETRIC_STRONG == 0)) {
            log("Authentication type must be strong to authenticate with crypto.")
            return
        }

        authenticateWithCrypto()
    }

    private fun canAuthenticateWithCrypto(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun authenticateWithCrypto() {
        val promptInfo = buildPromptInfo() ?: return
        generateAndStoreKeyInKeyStore()
        val crypto = getCrypto()

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun generateAndStoreKeyInKeyStore() {
        // Create key spec used to create a [KeyGenerator]. Defines things such as whether
        // authentication is required to use the key, allowed operations, etc.
        val keyPurpose = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        val builder = KeyGenParameterSpec.Builder(KEY_NAME, keyPurpose)
            .setBlockModes(BLOCK_MODE)
            .setEncryptionPaddings(ENCRYPTION_PADDING)

        // Require authentication to use the key
        builder.setUserAuthenticationRequired(true)

        // Require authentication for every single use of the key
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setUserAuthenticationParameters(0 /* timeout */, getSecretKeyType())
        } else {
            @Suppress("DEPRECATION")
            builder.setUserAuthenticationValidityDurationSeconds(-1)
        }
        val keySpec = builder.build()

        // Create a generator of keys for the AES algorithm using the android key store provider
        val keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM, KEYSTORE)

        // Initialize the key generator with the specified parameters/specs
        keyGenerator.init(keySpec)

        // Generate a secret key and store it
        keyGenerator.generateKey()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun getSecretKeyType(): Int {
        val strong = if (authenticatorStrong.isChecked) AUTH_BIOMETRIC_STRONG else 0
        val deviceCredential =
            if (authenticatorDeviceCredential.isChecked) AUTH_DEVICE_CREDENTIAL else 0
        return strong or deviceCredential
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCipher(): Cipher {
        return Cipher.getInstance("$KEY_ALGORITHM/$BLOCK_MODE/$ENCRYPTION_PADDING")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCrypto(): BiometricPrompt.CryptoObject {
        val cipher = getCipher()

        if (shouldEncrypt) { // Should encrypt
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        } else { // Should decrypt
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey())
        }

        return BiometricPrompt.CryptoObject(cipher)
    }

    /**
     * Returns the previously generated secret key from keystore.
     */
    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(KEYSTORE)
        keyStore.load(null)
        return keyStore.getKey(KEY_NAME, null) as SecretKey
    }
    // endregion Authentication

    @SuppressLint("SetTextI18n")
    private fun log(message: String) {
        val currentLogs = logs.text.toString()
        logs.text = "$message\n$currentLogs"
    }

    private fun clearLogs() {
        logs.text = ""
    }

    companion object {
        private const val KEY_NAME = "my-key-name"
        private const val KEYSTORE = "AndroidKeyStore"
        private const val IV = "my-iv"

        @RequiresApi(Build.VERSION_CODES.M)
        private const val KEY_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES

        @RequiresApi(Build.VERSION_CODES.M)
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC

        @RequiresApi(Build.VERSION_CODES.M)
        private const val ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    }
}