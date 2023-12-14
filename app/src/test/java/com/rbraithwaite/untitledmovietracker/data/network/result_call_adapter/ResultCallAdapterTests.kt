package com.rbraithwaite.untitledmovietracker.data.network.result_call_adapter

import com.google.gson.GsonBuilder
import com.rbraithwaite.untitledmovieapp.data.network.NetworkError
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResultDeserializer
import com.rbraithwaite.untitledmovieapp.data.network.result_call_adapter.ResultCallAdapterFactory
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import com.rbraithwaite.untitledmovietracker.test_utils.base_tests.RetrofitApiTests
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okio.buffer
import okio.source
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.nio.charset.StandardCharsets

class ResultCallAdapterTests: RetrofitApiTests() {

    lateinit var tmdbApiV3: TmdbApiV3

    @Before
    override fun setup() {
        super.setup()

        tmdbApiV3 = SingletonModule
            .createRetrofitBuilder(
                mockWebServer.url("/"),
                SingletonModule.createGson()
            )
            .build()
            .create(TmdbApiV3::class.java)
    }

    @Test
    fun callIsAdaptedToResult() = runTest {
        // GIVEN a retrofit api that returns a Result
        // -------------------------------------------

        val response = MockResponse()
        response.setBodyFromResourceFile("ResultCallAdapterTests_SearchMultiResponse.json")

        mockWebServer.enqueue(response)

        // WHEN that api method returning a Result is called
        // -------------------------------------------

        val result = tmdbApiV3.searchMulti("ignored query")

        // THEN the response was successfully adapted to a Result
        // -------------------------------------------
        assertThat(result.isSuccess, willBe(true))

        val searchMultiResults = result.getOrNull()!!

        assertThat(searchMultiResults.page, willBe(1))
        assertThat(searchMultiResults.totalPages, willBe(33))
        assertThat(searchMultiResults.totalResults, willBe(653))
        assertThat(searchMultiResults.results.size, willBe(20))

        val firstResult = searchMultiResults.results[0] as SearchMultiResult.TvShow

        assertThat(firstResult.id, willBe(209183))
        assertThat(firstResult.name, willBe("Better"))
    }

    // REFACTOR [23-12-2 1:44p.m.] -- move this.
    /**
     * @param filepath This path should be relative to the resources/ directory
     */
    fun MockResponse.setBodyFromResourceFile(filepath: String): MockResponse {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(filepath)
        val source = inputStream.source().buffer()

        this.setBody(source.readString(StandardCharsets.UTF_8))

        return this
    }

    @Test
    fun httpFailureTest() = runTest {
        // GIVEN a response with an http error code
        // -------------------------------------------

        val expectedCode = 404

        val response = MockResponse()
        response.setResponseCode(expectedCode)

        mockWebServer.enqueue(response)

        // WHEN a retrofit interface returning a result receives that response
        // -------------------------------------------

        val result = tmdbApiV3.searchMulti("ignored query")

        // THEN the Result is a failure with a NetworkError.Http
        // -------------------------------------------

        assertThat("", result.isFailure)
        assertThat("", result.exceptionOrNull() is NetworkError.Http)
        with (result.exceptionOrNull() as NetworkError.Http) {
            assertThat(this.code, willBe(expectedCode))
        }
    }
}