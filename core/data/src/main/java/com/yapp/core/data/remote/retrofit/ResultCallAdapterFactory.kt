package com.yapp.core.data.remote.retrofit

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.Optional

class ResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val innerType = getParameterUpperBound(0, returnType as ParameterizedType)

        val isOptional = (getRawType(innerType) == Optional::class.java)
        val actualType = if (isOptional && innerType is ParameterizedType) {
            getParameterUpperBound(0, innerType)
        } else {
            innerType
        }

        val baseResponseType = object : ParameterizedType {
            override fun getRawType(): Type = YappResponse::class.java
            override fun getOwnerType(): Type? = null
            override fun getActualTypeArguments(): Array<Type> = arrayOf(actualType)
        }

        return apiResultAdapter(baseResponseType, innerType)
    }

    private fun apiResultAdapter(
        returnType: ParameterizedType,
        innerType: Type
    ): CallAdapter<Type, out Call<out Any>> {
        return if (getRawType(innerType) == Optional::class.java) {
            OptionalApiResultCallAdapter(returnType)
        } else {
            ApiResultCallAdapter(returnType)
        }
    }
}