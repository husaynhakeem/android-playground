package com.husaynhakeem.runtimeonly

import com.husaynhakeem.compileonly.Logger

class RuntimeOnlyLogger : Logger {

    override fun log(message: String) {
        println("Runtime only logger - $message")
    }
}