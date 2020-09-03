package com.husaynhakeem.biometricsample.crypto

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
class CryptographyManagerApi30 : CryptographyManagerCommon() {

    override fun onNewCipherRequested() {
        // The secret key type may have change since the last time a cipher was needed.
        // Generate and store the key again
        generateAndStoreKey()
    }

    override fun requireAuthenticationForEveryKeyUse(builder: KeyGenParameterSpec.Builder) {
        builder.setUserAuthenticationParameters(0 /* timeout*/, keyType)
    }
}