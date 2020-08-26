package com.husaynhakeem.biometricsample

import android.hardware.biometrics.BiometricManager.Authenticators.*
import android.os.Bundle
import android.security.keystore.KeyProperties.AUTH_BIOMETRIC_STRONG
import android.security.keystore.KeyProperties.AUTH_DEVICE_CREDENTIAL
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.layout_authentication_confirmation.*
import kotlinx.android.synthetic.main.layout_authenticator_types.*
import kotlinx.android.synthetic.main.layout_configuration_change.*
import kotlinx.android.synthetic.main.layout_negative_button.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun getAuthenticationTypes(): Int {
        val strong = if (authenticatorStrong.isChecked) BIOMETRIC_STRONG else 0
        val weak = if (authenticatorWeak.isChecked) BIOMETRIC_WEAK else 0
        val deviceCredential = if (authenticatorDeviceCredential.isChecked) DEVICE_CREDENTIAL else 0
        return strong or weak or deviceCredential
    }

    private fun showNegativeButton(): Boolean {
        return negativeButton.isChecked
    }

    private fun showAuthenticationConfirmation(): Boolean {
        return authenticationConfirmation.isChecked
    }

    private fun keepAuthenticationDialogOnConfigurationChange(): Boolean {
        return configurationChange.isChecked
    }

    private fun getSecretKeyType(): Int {
        val strong = if (authenticatorStrong.isChecked) AUTH_BIOMETRIC_STRONG else 0
        val deviceCredential =
            if (authenticatorDeviceCredential.isChecked) AUTH_DEVICE_CREDENTIAL else 0
        return strong or deviceCredential
    }

    private fun canAuthentication() {
        val biometricManager = BiometricManager.from(this)
        val canAuthenticate: BiometricError = biometricManager.canAuthenticate()
        log(canAuthenticate.literal())
    }

    private fun authenticate() {
        // BiometricPrompt to launch an authentication operation
        val biometricPrompt = BiometricPrompt(
            this, // FragmentActivity
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    log("Authentication succeeded")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    log("Authentication errored out - Error code $errorCode - $errString")
                }

                override fun onAuthenticationFailed() {
                    log("Authentication failed")
                }
            }
        )

        // BiometricPrompt.Info to define the properties of the dialog
        val biometricInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("Subtitle")
            .setDescription("Description")
            .setNegativeButtonText("Negative text")
            .setConfirmationRequired(false)
            .setDeviceCredentialAllowed(true)
            .build()

        biometricPrompt.authenticate(biometricInfo)
    }

    private fun log(message: String) {
        Log.d(TAG, message)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}