package com.rbraithwaite.untitledmovietracker.data.network

import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovietracker.test_utils.ApiTestUtils
import com.rbraithwaite.untitledmovietracker.test_utils.enqueueResponseFromFile
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MockWebServerRule
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TmdbApiV3Tests {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    private lateinit var tmdbApiV3: TmdbApiV3

    @Before
    fun setup() {
        // api needs to be created late, to let the mock server start first
        tmdbApiV3 = ApiTestUtils.createTmdbApiV3(mockWebServerRule.server)
    }

    @Test
    fun getMovieCertifications_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_CertificationsMovieList.json")

        val result = tmdbApiV3.getMovieCertifications()

        assert(result.isSuccess)

        val certifications = result.getOrThrow().certifications
        assertThat(certifications.size, willBe(46))

        val certificationsCanada = certifications["CA"]!!
        assertThat(certificationsCanada.size, willBe(7))
        assertThat(certificationsCanada[4].certification, willBe("R"))
    }

    @Test
    fun getTvCertifications_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_CertificationsTvList.json")

        val result = tmdbApiV3.getTvCertifications()

        assert(result.isSuccess)

        val certifications = result.getOrThrow().certifications
        assertThat(certifications.size, willBe(40))

        val certificationsCanada = certifications["CA"]!!

        assertThat(certificationsCanada.size, willBe(7))
        assertThat(certificationsCanada[6].certification, willBe("18+"))
    }
}