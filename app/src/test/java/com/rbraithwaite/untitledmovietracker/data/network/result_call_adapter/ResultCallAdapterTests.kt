package com.rbraithwaite.untitledmovietracker.data.network.result_call_adapter

import com.rbraithwaite.untitledmovieapp.data.network.NetworkError
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovietracker.test_utils.ApiTestUtils
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MockWebServerRule
import com.rbraithwaite.untitledmovietracker.test_utils.setBodyFromResourceFile
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ResultCallAdapterTests {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    private lateinit var tmdbApiV3: TmdbApiV3

    @Before
    fun setup() {
        // api needs to be created late, to let the mock server start first
        tmdbApiV3 = ApiTestUtils.createTmdbApiV3(mockWebServerRule.server)
    }

    @Test
    fun callIsAdaptedToResult() = runTest {
        // GIVEN a retrofit api that returns a Result
        // -------------------------------------------

        // REFACTOR [24-02-8 11:17p.m.] -- use enqueueResponseFromFile().
        val response = MockResponse()
        response.setBodyFromResourceFile("ResultCallAdapterTests_SearchMultiResponse.json")

        mockWebServerRule.server.enqueue(response)

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

    @Test
    fun httpFailureTest() = runTest {
        // GIVEN a response with an http error code
        // -------------------------------------------

        val expectedCode = 404

        val response = MockResponse()
        response.setResponseCode(expectedCode)

        mockWebServerRule.server.enqueue(response)

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