package com.husaynhakeem.biometricsample

import androidx.biometric.BiometricManager.*

typealias BiometricError = Int

fun BiometricError.literal(): String {
    return when (this) {
        BIOMETRIC_SUCCESS -> "Biometric success"
        BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Biometric error - Hardware unavailable"
        BIOMETRIC_ERROR_NONE_ENROLLED -> "Biometric error - No biometrics enrolled"
        BIOMETRIC_ERROR_NO_HARDWARE -> "Biometric error - No biometric hardware"
        else -> "Unknown biometric error - $this"
    }
}