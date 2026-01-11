package com.rbraithwaite.yourmovielog.data.network.result_call_adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // Result is being used for suspend functions, which wrap the type in Call<>
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        require(returnType is ParameterizedType) {
            "Expected returnType to be ParameterizedType: $returnType"
        }

        val callType = getParameterUpperBound(0, returnType)

        if (getRawType(callType) != Result::class.java) {
            return null
        }

        require(callType is ParameterizedType) {
            "Expected callType to be ParameterizedType (which is weird... callType should be Result<>)"
        }

        val successType = getParameterUpperBound(0, callType)

        return ResultCallAdapter<Any>(successType)
    }
}
