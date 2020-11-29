package com.rinfinity.datastoresample.serializer

import androidx.datastore.core.Serializer
import com.rinfinity.datastoresample.model.UserModel
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception

object UserModelSerializer: Serializer<UserModel> {
    override val defaultValue: UserModel
        get() = UserModel.getDefaultInstance()

    override fun readFrom(input: InputStream): UserModel = try {
        UserModel.parseFrom(input)
    } catch (e: Exception) {
        throw Exception(e)
    }

    override fun writeTo(t: UserModel, output: OutputStream) = t.writeTo(output)
}