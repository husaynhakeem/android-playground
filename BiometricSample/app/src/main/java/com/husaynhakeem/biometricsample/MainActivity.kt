package com.husaynhakeem.biometricsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BiometricManager to check whether we can authenticate
        val biometricManager = BiometricManager.from(this)
        val canAuthenticate = biometricManager.canAuthenticate()
        Log.d(TAG, "Can authenticate: $canAuthenticate")
    }

    private fun authenticate() {
        // BiometricPrompt to launch an authentication operation
        val biometricPrompt = BiometricPrompt(
                this, // FragmentActivity
                ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        Log.d(TAG, "Authentication succeeded")
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        Log.d(TAG, "Authentication errored out - Error code $errorCode - $errString")
                    }

                    override fun onAuthenticationFailed() {
                        Log.d(TAG, "Authentication failed")
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

    companion object {
        private const val TAG = "MainActivity"
    }
}