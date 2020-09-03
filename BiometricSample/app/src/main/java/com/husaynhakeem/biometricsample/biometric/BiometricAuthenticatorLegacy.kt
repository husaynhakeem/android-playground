package com.husaynhakeem.biometricsample.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

class BiometricAuthenticatorLegacy(activity: FragmentActivity, listener: Listener) :
    BiometricAuthenticator(activity, listener) {

    @Suppress("DEPRECATION")
    override fun canAuthenticate(context: Context) {
        val biometricManager = BiometricManager.from(context)
        val canAuthenticate = biometricManager.canAuthenticate()
        listener.onNewMessage(getBiometricAvailability(canAuthenticate))
    }

    override fun authenticateAndEncrypt(context: Context) {
        listener.onNewMessage("Cannot use biometrics with cryptography on this API level.")
    }

    override fun authenticateAndDecrypt(context: Context) {
        listener.onNewMessage("Cannot use biometrics with cryptography on this API level.")
    }

    @Suppress("DEPRECATION")
    override fun setAllowedAuthenticators(builder: BiometricPrompt.PromptInfo.Builder) {
        builder.setDeviceCredentialAllowed(isDeviceCredentialAuthenticationEnabled)
    }
}