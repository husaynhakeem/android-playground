package com.husaynhakeem.biometricsample.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
class CryptographyManagerApi23 : CryptographyManagerCommon() {

    init {
        generateAndStoreKey()
    }

    override fun onNewCipherRequested() {
        // Do nothing. The key has already been created and stored in the keyStore
    }

    @Suppress("DEPRECATION")
    override fun requireAuthenticationForEveryKeyUse(builder: KeyGenParameterSpec.Builder) {
        builder.setUserAuthenticationValidityDurationSeconds(-1)
    }
}