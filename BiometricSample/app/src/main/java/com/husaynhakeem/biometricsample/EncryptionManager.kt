package com.husaynhakeem.biometricsample

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

@RequiresApi(Build.VERSION_CODES.M)
class EncryptionManager(private val secretKeyType: Int) {

    private lateinit var iv: ByteArray

    init {
        // Create and configure a new [KeyGenParameterSpec] instance
        val keyGenParameterSpec = buildKeyGenParamSpec()

        // Create a generator of keys for the AES algorithm using the android key store provider
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_PROVIDER)

        // Initialize the key generator with the specified parameters/specs
        keyGenerator.init(keyGenParameterSpec)

        // Generate a secret key and store it
        keyGenerator.generateKey()
    }

    private fun buildKeyGenParamSpec(): KeyGenParameterSpec {

        // Specify the purpose of the key, in this case, it's used to both encrypt and decrypt data
        val keyPurpose = KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT

        // Initialize builder with the key alias, and its purpose
        val builder = KeyGenParameterSpec.Builder(KEY_ALIAS, keyPurpose)

        //
        builder.setBlockModes(KeyProperties.BLOCK_MODE_GCM)

        //
        builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)

        // Require authentication to use the key
        builder.setUserAuthenticationRequired(true)

        // Require authentication for every single use of the key
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            builder.setUserAuthenticationParameters(0 /* timeout*/, secretKeyType)
        } else {
            @Suppress("DEPRECATION")
            builder.setUserAuthenticationValidityDurationSeconds(-1)
        }

        //
        builder.setRandomizedEncryptionRequired(true)

        return builder.build()
    }

    fun getCryptoToEncrypt(): BiometricPrompt.CryptoObject {

        // Get the key
        val keyStore = KeyStore.getInstance(KEY_PROVIDER)
        keyStore.load(null)
        val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey

        // Create a Cipher
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        iv = cipher.iv

        return BiometricPrompt.CryptoObject(cipher)
    }

    @Throws(IllegalStateException::class)
    fun getCryptoToDecrypt(): BiometricPrompt.CryptoObject {
        if (!::iv.isInitialized) {
            throw IllegalStateException("Must encrypt data before decrypting it")
        }

        // Get the key
        val keyStore = KeyStore.getInstance(KEY_PROVIDER)
        keyStore.load(null)
        val secretKey = keyStore.getKey(KEY_ALIAS, null) as SecretKey

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        return BiometricPrompt.CryptoObject(cipher)
    }

    // TODO: Fix bug where encrypt/decrypt/decrypt causes the app to crash
    fun encrypt(cipher: Cipher, data: String): ByteArray {
        return cipher.doFinal(data.toByteArray(Charsets.UTF_8))
    }

    fun decrypt(cipher: Cipher, data: ByteArray): String {
        val decryptedData = cipher.doFinal(data)
        return String(decryptedData, Charsets.UTF_8)
    }

    companion object {
        private const val KEY_PROVIDER = "AndroidKeyStore"
        private const val KEY_ALIAS = "key-auth-sample"
        private const val TRANSFORMATION =
            "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_GCM}/${KeyProperties.ENCRYPTION_PADDING_NONE}"
    }
}