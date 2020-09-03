package com.husaynhakeem.biometricsample.biometric

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.husaynhakeem.biometricsample.crypto.EncryptionMode

@RequiresApi(Build.VERSION_CODES.M)
class BiometricAuthenticatorApi23(activity: FragmentActivity, listener: Listener) :
    BiometricAuthenticator(activity, listener) {

    @Suppress("DEPRECATION")
    override fun canAuthenticate(context: Context) {
        val biometricManager = BiometricManager.from(context)
        val canAuthenticate = biometricManager.canAuthenticate()
        listener.onNewMessage(getBiometricAvailability(canAuthenticate))
    }

    override fun authenticateAndEncrypt(context: Context) {
        val promptInfo = buildPromptInfo(context) ?: return
        val cipher = cryptographyManager.getCipherForEncryption()
        val crypto = BiometricPrompt.CryptoObject(cipher)
        encryptionMode = EncryptionMode.ENCRYPT

        try {
            biometricPrompt.authenticate(promptInfo, crypto)
        } catch (exception: IllegalArgumentException) {
            listener.onNewMessage("Authentication with crypto error - ${exception.message}")
        }
    }

    override fun authenticateAndDecrypt(context: Context) {
        val promptInfo = buildPromptInfo(context) ?: return
        val cipher = cryptographyManager.getCipherForDecryption(encryptedData.initializationVector)
        val crypto = BiometricPrompt.CryptoObject(cipher)
        encryptionMode = EncryptionMode.DECRYPT

        try {
            biometricPrompt.authenticate(promptInfo, crypto)
        } catch (exception: IllegalArgumentException) {
            listener.onNewMessage("Authentication with crypto error - ${exception.message}")
        }
    }

    @Suppress("DEPRECATION")
    override fun setAllowedAuthenticators(builder: BiometricPrompt.PromptInfo.Builder) {
        builder.setDeviceCredentialAllowed(isDeviceCredentialAuthenticationEnabled)
    }
}
