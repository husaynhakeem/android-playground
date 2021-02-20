package com.husaynhakeem.datastoresample.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.husaynhakeem.datastoresample.User
import java.io.InputStream
import java.io.OutputStream

class UserSerializer : Serializer<User> {

    override val defaultValue: User
        get() = User.getDefaultInstance()

    override fun readFrom(input: InputStream): User {
        try {
            return User.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun writeTo(user: User, output: OutputStream) {
        user.writeTo(output)
    }
}