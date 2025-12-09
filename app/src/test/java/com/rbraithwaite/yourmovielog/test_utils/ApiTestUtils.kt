package com.rbraithwaite.yourmovielog.test_utils

import com.rbraithwaite.yourmovielog.data.network.TmdbApiV3
import com.rbraithwaite.yourmovielog.di.SingletonModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

object ApiTestUtils {
    fun createTmdbApiV3(mockWebServer: MockWebServer): TmdbApiV3 {
        return SingletonModule
            .createRetrofitBuilder(
                mockWebServer.url("/"),
                SingletonModule.createGson()
            )
            .build()
            .create(TmdbApiV3::class.java)
    }
}

/**
 * @param filepath This path should be relative to the resources/ directory
 */
fun MockResponse.setBodyFromResourceFile(filepath: String): MockResponse {
    val inputStream = javaClass.classLoader!!.getResourceAsStream(filepath)
    val source = inputStream.source().buffer()

    this.setBody(source.readString(StandardCharsets.UTF_8))

    return this
}

/**
 * @param filepath This path should be relative to the resources/ directory
 */
fun MockWebServer.enqueueResponseFromFile(filepath: String) {
    val response = MockResponse()
    response.setBodyFromResourceFile(filepath)
    enqueue(response)
}