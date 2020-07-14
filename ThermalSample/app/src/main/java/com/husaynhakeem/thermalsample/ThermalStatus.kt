package com.husaynhakeem.thermalsample

import android.os.PowerManager.*

typealias ThermalStatus = Int

fun ThermalStatus.literal(): String {
    return when (this) {
        THERMAL_STATUS_NONE -> "None. Not under throttling."
        THERMAL_STATUS_LIGHT -> "Light. Light throttling where UX is not impacted."
        THERMAL_STATUS_MODERATE -> "Moderate. Moderate throttling where UX is not largely impacted."
        THERMAL_STATUS_SEVERE -> "Severe. Severe throttling where UX is largely impacted."
        THERMAL_STATUS_CRITICAL -> "Critical. Platform has done everything to reduce power."
        THERMAL_STATUS_EMERGENCY -> "Emergency. Key components in platform are shutting down due to thermal condition. Device functionalities will be limited."
        THERMAL_STATUS_SHUTDOWN -> "Shutdown. Need shutdown immediately."
        else -> throw IllegalArgumentException("Unknown thermal status $this")
    }
}