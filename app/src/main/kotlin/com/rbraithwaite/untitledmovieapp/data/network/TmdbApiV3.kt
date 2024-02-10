package com.rbraithwaite.untitledmovieapp.data.network

import com.rbraithwaite.untitledmovieapp.data.network.models.CertificationsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.Configuration
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.JobsConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiV3 {
    object Constants {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    /**
     * Returns movie AND tv show results based on the query.
     *
     * @param query The string search query, e.g. "the godfather"
     * @param includeAdult whether to include adult film results
     * @param language the language of the returned results
     * @param pageNumber The page number of results to get (refer to {@link SearchMultiResults.totalPages})
     */
    @GET("search/multi")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        // REFACTOR [23-11-21 11:16p.m.] -- hardcoded language string.
        @Query("language") language: String = "en-US",
        @Query("page") pageNumber: Int = 1
    ): Result<SearchMultiResults>


    /**
     * Returns the static list of movie certifications for different countries. Like G, 14A, R, etc.
     */
    @GET("certification/movie/list")
    suspend fun getMovieCertifications(): Result<CertificationsResponse>

    /**
     * Returns the static list of tv certifications for different countries. Like TV-PG, TV-14,
     * YV-MA, etc.
     */
    @GET("certification/tv/list")
    suspend fun getTvCertifications(): Result<CertificationsResponse>

    /**
     * Returns static data to help integrate with this API.
     */
    @GET("configuration")
    suspend fun getConfiguration(): Result<Configuration>

    /**
     * Returns a static list of the countries used by TMDB
     */
    @GET("configuration/countries")
    suspend fun getCountryConfiguration(): Result<List<CountryConfig>>

    /**
     * Returns a static list of the movie-industry departments and jobs used by TMDB
     */
    @GET("configuration/jobs")
    suspend fun getJobsConfiguration(): Result<List<JobsConfig>>
}