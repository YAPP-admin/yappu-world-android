package com.yapp.core.data.remote.retrofit

import com.yapp.model.exceptions.YappServerError
import kotlinx.serialization.json.Json
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type

internal class ApiResultCallAdapter<R>(
    private val successType: Type,
) : CallAdapter<R, Call<R>> {
    override fun adapt(call: Call<R>): Call<R> = ApiResultCall(call, successType)
    override fun responseType(): Type = successType
}

private class ApiResultCall<R>(
    private val delegate: Call<R>,
    private val successType: Type,
) : Call<R> {

    override fun enqueue(callback: Callback<R>) = delegate.enqueue(
        object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                if (response.isSuccessful.not()) {
                    handleHttpError(response, callback)
                    return
                }

                @Suppress("UNCHECKED_CAST")
                val body = response.body() as? YappResponse<R>

                if (body != null) {
                    callback.onResponse(this@ApiResultCall, Response.success(body.data))
                    return
                }


                @Suppress("UNCHECKED_CAST")
                callback.onResponse(this@ApiResultCall, Response.success(Unit as R))
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                callback.onFailure(this@ApiResultCall, throwable)
            }
        },
    )

    private fun handleHttpError(response: Response<R>, callback: Callback<R>) {
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
            callback.onFailure(this@ApiResultCall, exception)
        }.onFailure {
            val exception = handleCommonError(response.code())
            callback.onFailure(this@ApiResultCall, exception)
        }
    }

    override fun clone(): Call<R> = ApiResultCall(delegate.clone(), successType)

    override fun execute(): Response<R> =
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