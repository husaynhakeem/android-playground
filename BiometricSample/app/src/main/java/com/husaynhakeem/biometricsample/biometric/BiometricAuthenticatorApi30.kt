package com.husaynhakeem.biometricsample.biometric

import android.content.Context
import android.hardware.biometrics.BiometricManager.Authenticators.*
import android.os.Build
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.husaynhakeem.biometricsample.crypto.EncryptionMode

@RequiresApi(Build.VERSION_CODES.R)
class BiometricAuthenticatorApi30(activity: FragmentActivity, listener: Listener) :
    BiometricAuthenticator(activity, listener) {

    override fun canAuthenticate(context: Context) {
        val biometricManager = BiometricManager.from(context)
        val canAuthenticate = biometricManager.canAuthenticate(getAuthenticationTypes())
        listener.onNewMessage(getBiometricAvailability(canAuthenticate))
    }

    override fun authenticateAndEncrypt(context: Context) {
        if (!canAuthenticateWithCrypto()) {
            return
        }

        val promptInfo = buildPromptInfo(context) ?: return
        cryptographyManager.setSecretKeyType(getSecretKeyType())
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
        if (!canAuthenticateWithCrypto()) {
            return
        }

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

    override fun setAllowedAuthenticators(builder: BiometricPrompt.PromptInfo.Builder) {
        builder.setAllowedAuthenticators(getAuthenticationTypes())
    }

    private fun getAuthenticationTypes(): Int {
        var authenticators = 0
        if (isStrongAuthenticationEnabled) {
            authenticators = authenticators or BIOMETRIC_STRONG
        }
        if (isWeakAuthenticationEnabled) {
            authenticators = authenticators or BIOMETRIC_WEAK
        }
        if (isDeviceCredentialAuthenticationEnabled) {
            authenticators = authenticators or DEVICE_CREDENTIAL
        }
        return authenticators
    }

    private fun canAuthenticateWithCrypto(): Boolean {
        if (getSecretKeyType() and KeyProperties.AUTH_BIOMETRIC_STRONG == 0) {
            listener.onNewMessage(
                "Authentication type must be strong to authenticate with crypto" +
                        " on API levels >= 30"
            )
            return false
        }
        return true
    }

    private fun getSecretKeyType(): Int {
        val strong = if (isStrongAuthenticationEnabled) KeyProperties.AUTH_BIOMETRIC_STRONG else 0
        val deviceCredential =
            if (isDeviceCredentialAuthenticationEnabled) KeyProperties.AUTH_DEVICE_CREDENTIAL else 0
        return strong or deviceCredential
    }
}
