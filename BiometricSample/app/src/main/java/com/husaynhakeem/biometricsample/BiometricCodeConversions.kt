package com.husaynhakeem.biometricsample

import androidx.biometric.BiometricManager.*
import androidx.biometric.BiometricPrompt.*


fun getBiometricAvailability(availability: Int): String {
    return when (availability) {
        BIOMETRIC_SUCCESS -> "Biometric success"
        BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Biometric error - Hardware unavailable"
        BIOMETRIC_ERROR_NONE_ENROLLED -> "Biometric error - No biometrics enrolled"
        BIOMETRIC_ERROR_NO_HARDWARE -> "Biometric error - No biometric hardware"
        else -> "Unknown biometric error - $availability"
    }
}

fun getBiometricError(error: Int): String {
    return when (error) {
        ERROR_HW_UNAVAILABLE -> "The hardware is unavailable"
        ERROR_UNABLE_TO_PROCESS -> "The sensor is unable to process the current image"
        ERROR_TIMEOUT -> "The current request has been running too long"
        ERROR_NO_SPACE -> "Not enough storage to complete the operation"
        ERROR_CANCELED -> "The operation was cancelled due to the sensor being unavailable"
        ERROR_LOCKOUT -> "The operation was cancelled due to too many attempts"
        ERROR_VENDOR -> "Vendor specific error"
        ERROR_LOCKOUT_PERMANENT -> "Permanent lockout until the user unlocks with strong authentication (PIN/pattern/Password)"
        ERROR_USER_CANCELED -> "The operation was cancelled by the user"
        ERROR_NO_BIOMETRICS -> "The user doesn't have any biometrics enrolled"
        ERROR_HW_NOT_PRESENT -> "The device does not have a biometric sensor"
        ERROR_NEGATIVE_BUTTON -> "The user pressed the negative button"
        ERROR_NO_DEVICE_CREDENTIAL -> "The device does not have pin, pattern or password set up"
        else -> ""
    }
}

fun getAuthenticationType(type: Int): String {
    return when (type) {
        AUTHENTICATION_RESULT_TYPE_BIOMETRIC -> "Biometric"
        AUTHENTICATION_RESULT_TYPE_DEVICE_CREDENTIAL -> "Device credential"
        AUTHENTICATION_RESULT_TYPE_UNKNOWN -> "Unknown"
        else -> "Unknown authentication type - $type"
    }
}