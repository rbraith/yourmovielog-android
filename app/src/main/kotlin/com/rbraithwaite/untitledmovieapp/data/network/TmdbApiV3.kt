package com.rbraithwaite.untitledmovieapp.data.network

import com.rbraithwaite.untitledmovieapp.data.network.models.Certification
import com.rbraithwaite.untitledmovieapp.data.network.models.CertificationsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyDetails
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyLogos
import com.rbraithwaite.untitledmovieapp.data.network.models.Configuration
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryTimezones
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverMovieResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.JobsConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    /**
     * Returns a static list of the timezones used by TMDB
     */
    @GET("configuration/timezones")
    suspend fun getTimezonesConfiguration(): Result<List<CountryTimezones>>

    /**
     * Returns the details of the company with the given id.
     */
    @GET("company/{company_id}")
    suspend fun getCompanyDetails(@Path("company_id") companyId: Long): Result<CompanyDetails>

    /**
     * Returns logo information for the company with the given id.
     */
    @GET("company/{company_id}/images")
    suspend fun getCompanyLogos(@Path("company_id") companyId: Long): Result<CompanyLogos>

    /**
     * @param region ISO-3166-1 country code (https://en.wikipedia.org/wiki/ISO_3166-1)
     */
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("certification") certification: String? = null,
        @Query("certification.gte") certificationGte: String? = null,
        @Query("certification.lte") certificationLte: String? = null,
        @Query("certification_country") certificationCountry: String? = null,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        // REFACTOR [24-02-10 10:04p.m.] -- hardcoded language.
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("primary_release_date.gte") primaryReleaseDateGte: String? = null,
        @Query("primary_release_date.lte") primaryReleaseDateLte: String? = null,
        @Query("region") region: String? = null,
        @Query("release_date.gte") releaseDateGte: String? = null,
        @Query("release_date.lte") releaseDateLte: String? = null,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("vote_average.gte") voteAverageGte: Float? = null,
        @Query("vote_average.lte") voteAverageLte: Float? = null,
        @Query("vote_count.gte") voteCountGte: Float? = null,
        @Query("vote_count.lte") voteCountLte: Float? = null,
        @Query("watch_region") watchRegion: String? = null,
        @Query("with_cast") withCast: String? = null,
        @Query("with_companies") withCompanies: String? = null,
        @Query("with_crew") withCrew: String? = null,
        @Query("with_genres") withGenres: String? = null,
        @Query("with_keywords") withKeywords: String? = null,
        @Query("with_origin_country") withOriginalCountry: String? = null,
        @Query("with_original_language") withOriginLanguage: String? = null,
        @Query("with_people") withPeople: String? = null,
        @Query("with_release_type") withReleaseType: String? = null,
        @Query("with_runtime.gte") withRuntimeGte: Int? = null,
        @Query("with_runtime.lte") withRuntimeLte: Int? = null,
        @Query("with_watch_monetization_types") withWatchMonetizationTypes: String? = null,
        @Query("with_watch_providers") withWatchProviders: String? = null,
        @Query("without_companies") withoutCompanies: String? = null,
        @Query("without_genres") withoutGenres: String? = null,
        @Query("without_keywords") withoutKeywords: String? = null,
        @Query("without_watch_providers") withoutWatchProviders: String? = null,
        @Query("year") year: Int? = null
    ): Result<DiscoverMovieResponse>
}