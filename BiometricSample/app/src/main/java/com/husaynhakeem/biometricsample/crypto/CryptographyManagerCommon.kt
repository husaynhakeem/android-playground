package com.husaynhakeem.biometricsample.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

@RequiresApi(Build.VERSION_CODES.M)
abstract class CryptographyManagerCommon : CryptographyManager {

    protected var keyType: Int = INVALID_KEY_TYPE
        get() {
            if (field == INVALID_KEY_TYPE) {
                throw IllegalStateException(
                    "Secret key type is invalid. Did you forget to call " +
                            "CryptographyManager#setSecretKeyType()?"
                )
            }
            return field
        }

    abstract fun onNewCipherRequested()

    abstract fun requireAuthenticationForEveryKeyUse(builder: KeyGenParameterSpec.Builder)

    override fun canUseCrypto() = true

    override fun getCipherForEncryption(): Cipher {
        onNewCipherRequested()
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        return cipher
    }

    override fun getCipherForDecryption(initializationVector: ByteArray): Cipher {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, initializationVector)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        return cipher
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(KEY_PROVIDER)
        keyStore.load(null)
        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }

    override fun encrypt(plainData: String, cipher: Cipher): EncryptedData {
        val encrypted = cipher.doFinal(plainData.toByteArray(Charsets.UTF_8))
        return EncryptedData(encrypted, cipher.iv)
    }

    override fun decrypt(encryptedData: ByteArray, cipher: Cipher): String {
        val decryptedData = cipher.doFinal(encryptedData)
        return String(decryptedData, Charsets.UTF_8)
    }

    override fun setSecretKeyType(keyType: Int) {
        this.keyType = keyType
    }

    // region Generate and store the key in the KeyStore
    protected fun generateAndStoreKey() {

        // Create a generator of keys for the AES algorithm using the android key store provider
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_PROVIDER)

        // Initialize the key generator with the specified parameters/specs
        keyGenerator.init(buildKeyGenParamSpec())

        // Generate a secret key and store it
        keyGenerator.generateKey()
    }

    /** Creates and configures a new [KeyGenParameterSpec] instance. */
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
        // builder.setUserAuthenticationRequired(true)

        // Require authentication for every single use of the key
        requireAuthenticationForEveryKeyUse(builder)

        //
        builder.setRandomizedEncryptionRequired(true)

        return builder.build()
    }
    // endregion Generate and store the key in the KeyStore

    companion object {
        private const val INVALID_KEY_TYPE = -1
        private const val KEY_PROVIDER = "AndroidKeyStore"
        private const val KEY_ALIAS = "key-biometric-sample"
        private const val TRANSFORMATION =
            "${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_GCM}/${KeyProperties.ENCRYPTION_PADDING_NONE}"
    }
}