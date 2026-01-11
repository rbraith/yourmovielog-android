package com.rbraithwaite.yourmovielog.data.network.result_call_adapter

import com.rbraithwaite.yourmovielog.data.network.NetworkError
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(
    private val delegate: Call<T>
) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result: Result<T> = if (response.isSuccessful) {
                    response.body()?.let { Result.success(it) } ?: Result.failure(NetworkError.Unknown())
                } else {
                    Result.failure(NetworkError.Http(response.code()))
                }

                callback.onResponse(
                    this@ResultCall,
                    Response.success(result)
                )
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkError = if (t is IOException) {
                    NetworkError.Network(t)
                } else {
                    NetworkError.Unknown(t)
                }

                // The trick is always using Response.success() so retrofit doesn't throw and
                // simply returns the failure Result
                callback.onResponse(
                    this@ResultCall,
                    Response.success(Result.failure(networkError))
                )
            }
        })
    }

    override fun clone(): Call<Result<T>> = ResultCall(delegate)

    override fun execute(): Response<Result<T>> {
        throw UnsupportedOperationException("ResultCall does not support execute()")
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
