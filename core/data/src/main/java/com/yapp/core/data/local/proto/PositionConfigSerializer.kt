package com.yapp.core.data.local.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.yapp.core.data.PositionConfigs
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class PositionConfigSerializer @Inject constructor() : Serializer<PositionConfigs> {
    override val defaultValue: PositionConfigs = PositionConfigs.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): PositionConfigs {
        return try {
            PositionConfigs.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: PositionConfigs, output: OutputStream) {
        t.writeTo(output)
    }
}
