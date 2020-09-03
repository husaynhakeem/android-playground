package com.husaynhakeem.biometricsample.crypto

import javax.crypto.Cipher

class CryptographyManagerLegacy : CryptographyManager {

    override fun canUseCrypto() = false

    override fun getCipherForEncryption(): Cipher {
        throw UnsupportedOperationException(
            "Using biometrics with cryptography isn't supported " +
                    "on this API level. "
        )
    }

    override fun getCipherForDecryption(initializationVector: ByteArray): Cipher {
        throw UnsupportedOperationException(
            "Using biometrics with cryptography isn't supported " +
                    "on this API level. "
        )
    }

    override fun encrypt(plainData: String, cipher: Cipher): EncryptedData {
        throw UnsupportedOperationException(
            "Using biometrics with cryptography isn't supported " +
                    "on this API level. "
        )
    }

    override fun decrypt(encryptedData: ByteArray, cipher: Cipher): String {
        throw UnsupportedOperationException(
            "Using biometrics with cryptography isn't supported " +
                    "on this API level. "
        )
    }

    override fun setSecretKeyType(keyType: Int) {
        throw UnsupportedOperationException(
            "Using biometrics with cryptography isn't supported " +
                    "on this API level. "
        )
    }
}