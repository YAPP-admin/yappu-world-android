package com.yapp.core.data.remote.retrofit

import com.yapp.model.exceptions.YappServerError
import kotlinx.serialization.json.Json
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.Optional

internal class OptionalApiResultCallAdapter<R: Any>(
    private val successType: Type,
) : CallAdapter<R, Call<Optional<R>>> {
    override fun adapt(call: Call<R>): Call<Optional<R>> = OptionalApiResultCall(call, successType)
    override fun responseType(): Type = successType
}

private class OptionalApiResultCall<R: Any>(
    private val delegate: Call<R>,
    private val successType: Type,
) : Call<Optional<R>> {

    override fun enqueue(callback: Callback<Optional<R>>) = delegate.enqueue(
        object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (response.isSuccessful.not()) {
                    handleHttpError(response, callback)
                    return
                }

                val body = response.body()

                if (body != null) {
                    @Suppress("UNCHECKED_CAST")
                    callback.onResponse(
                        this@OptionalApiResultCall,
                        Response.success(
                            Optional.ofNullable((body as YappResponse<R>).data)
                        )
                    )
                    return
                }

                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    callback.onResponse(this@OptionalApiResultCall, Response.success(Optional.ofNullable(Unit as R)))
                } else {
                    callback.onFailure(
                        this@OptionalApiResultCall,
                        Exception("Body가 존재하지 않지만, Unit 이외의 타입으로 정의했습니다.")
                    )
                }

            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                callback.onFailure(this@OptionalApiResultCall, throwable)
            }
        },
    )

    private fun handleHttpError(
        response: Response<R>,
        callback: Callback<Optional<R>>
    ) {
        kotlin.runCatching {
            val errorBody = response.errorBody()?.string()
            json.decodeFromString<YappErrorResponse>(errorBody!!)
        }.onSuccess { errorBody ->
            val exception = YappServerError
                .valueOf(errorBody.errorCode)
                .exception
                .apply {
                    when (errorBody.errorCode) {
                        YappServerError.COM_0001.name, YappServerError.COM_0002.name -> {
                            setMessage(errorBody.message)
                        }
                        else -> setMessage(message)
                    }
                }
            callback.onFailure(this@OptionalApiResultCall, exception)
        }.onFailure {
            val exception = handleCommonError(response.code())
            callback.onFailure(this@OptionalApiResultCall, exception)
        }
    }

    override fun clone(): Call<Optional<R>> = OptionalApiResultCall(delegate.clone(), successType)

    override fun execute(): Response<Optional<R>> =
        throw UnsupportedOperationException("This adapter doesn't support sync execution")

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    companion object {
        private val json = Json { encodeDefaults = true }
    }
}