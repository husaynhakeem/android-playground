package com.husaynhakeem.otherruntimeonly

import com.husaynhakeem.compileonly.Logger

class OtherRuntimeOnlyLogger : Logger {

    override fun log(message: String) {
        println("Other runtime only logger - $message")
    }
}