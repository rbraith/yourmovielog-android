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

    companion object {
        private const val RESOURCES_DIR = "TmdbApiV3Tests/"
    }

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

    @Test
    fun getConfiguration_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_Configuration.json")

        val result = tmdbApiV3.getConfiguration()

        assert(result.isSuccess)

        val configuration = result.getOrThrow()
        assertThat(configuration.images.baseUrl, willBe("http://image.tmdb.org/t/p/"))
        assertThat(configuration.images.posterSizes.size, willBe(7))
        assertThat(configuration.changeKeys.size, willBe(53))
    }

    @Test
    fun getCountryConfiguration_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_ConfigurationCountries.json")

        val result = tmdbApiV3.getCountryConfiguration()

        assert(result.isSuccess)

        val countries = result.getOrThrow()
        assertThat(countries.size, willBe(251))
        assertThat(countries.first().englishName, willBe("Andorra"))
    }

    @Test
    fun getJobsConfiguration_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_ConfigurationJobs.json")

        val result = tmdbApiV3.getJobsConfiguration()

        assert(result.isSuccess)

        val jobs = result.getOrThrow()
        assertThat(jobs.size, willBe(12))
        assertThat(jobs.first().department, willBe("Writing"))
        assertThat(jobs.first().jobs.size, willBe(45))
    }

    @Test
    fun getTimezonesConfiguration_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_ConfigurationTimezones.json")

        val result = tmdbApiV3.getTimezonesConfiguration()

        assert(result.isSuccess)

        val countryTimezones = result.getOrThrow()
        assertThat(countryTimezones.size, willBe(249))
        assertThat(countryTimezones.first().countryIso, willBe("AD"))
        assertThat(countryTimezones.first().zoneNames.size, willBe(1))
    }

    @Test
    fun getCompanyDetails_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile("TmdbApiV3Tests_CompanyDetails.json")

        val result = tmdbApiV3.getCompanyDetails(123)

        assert(result.isSuccess)

        val companyDetails = result.getOrThrow()
        assertThat(companyDetails.name, willBe("Lionsgate"))
    }

    @Test
    fun getCompanyLogos_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile(RESOURCES_DIR + "CompanyImages.json")

        val result = tmdbApiV3.getCompanyLogos(123)

        assert(result.isSuccess)

        val companyLogos = result.getOrThrow()
        assertThat(companyLogos.companyId, willBe(1632))
        assertThat(companyLogos.logos.size, willBe(1))
        assertThat(companyLogos.logos.first().id, willBe("5abefb980e0a264a540204cf"))
    }

    @Test
    fun discoverMovies_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile(RESOURCES_DIR + "DiscoverMovie.json")

        val result = tmdbApiV3.discoverMovies()

        assert(result.isSuccess)

        val response = result.getOrThrow()
        assertThat(response.totalResults, willBe(816588))
        assertThat(response.results.size, willBe(20))
        assertThat(response.results.first().title, willBe("Five Nights at Freddy's"))
    }

    @Test
    fun discoverTvShows_successTest() = runTest {
        mockWebServerRule.server.enqueueResponseFromFile(RESOURCES_DIR + "DiscoverTv.json")

        val result = tmdbApiV3.discoverTvShows()

        assert(result.isSuccess)

        val response = result.getOrThrow()
        assertThat(response.totalResults, willBe(164657))
        assertThat(response.results.size, willBe(20))
        assertThat(response.results.first().name, willBe("The Tonight Show Starring Jimmy Fallon"))
    }
}