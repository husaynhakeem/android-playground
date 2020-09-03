package com.husaynhakeem.biometricsample.crypto

import android.os.Build
import javax.crypto.Cipher

interface CryptographyManager {

    fun canUseCrypto(): Boolean

    fun getCipherForEncryption(): Cipher

    fun getCipherForDecryption(initializationVector: ByteArray): Cipher

    fun encrypt(plainData: String, cipher: Cipher): EncryptedData

    fun decrypt(encryptedData: ByteArray, cipher: Cipher): String

    fun setSecretKeyType(keyType: Int)

    companion object {

        /**
         * Returns a new instance of [CryptographyManager], which is:
         * - [CryptographyManagerLegacy] if the device is running on API level < 23
         * - [CryptographyManagerApi23] if the device is running on API level >= 23 and < 30
         * - [CryptographyManagerApi30] if the device is running on API level > 30
         */
        fun instance(): CryptographyManager {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return CryptographyManagerLegacy()
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                return CryptographyManagerApi23()
            }
            return CryptographyManagerApi30()
        }
    }
}