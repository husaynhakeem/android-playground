package com.husaynhakeem.instrumentationsample

import com.google.common.truth.Truth.assertThat

class LogAssert(private val messages: List<String>) {

    private var index = 0

    fun hasMessage(message: String): LogAssert {
        assertThat(messages[index++]).isEqualTo(message)
        return this
    }

    fun hasNoMoreMessages() {
        assertThat(index).isEqualTo(messages.size)
    }

    companion object {
        fun assertLog(messages: List<String>): LogAssert {
            return LogAssert(messages)
        }
    }
}