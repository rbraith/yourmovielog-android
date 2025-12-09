package com.rbraithwaite.yourmovielog.data.network.result_call_adapter

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultCallAdapter<T>(
    private val successType: Type
): CallAdapter<T, Call<Result<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<Result<T>> {
        return ResultCall(call)
    }
}