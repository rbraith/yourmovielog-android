package com.rbraithwaite.untitledmovieapp.data.network

import com.rbraithwaite.untitledmovieapp.data.network.models.Certification
import com.rbraithwaite.untitledmovieapp.data.network.models.CertificationsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyDetails
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyLogos
import com.rbraithwaite.untitledmovieapp.data.network.models.Configuration
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryTimezones
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverMovieResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverTvResponse
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
     * Advanced movie search.
     *
     * @param certification used with certification_country. Should be a code like R, PG-13, etc.
     * @param certificationGte similar format to certification. Find movies with greater than or
     * equal certs.
     * @param certificationLte similar format to certification. Find movies with less than or equal
     * certs.
     * @param certificationCountry The country to use as a reference for the certification codes
     * in other params like 'certification'.
     * @param includeAdult should adult film results be included?
     * @param includeVideo Idk what this does
     * @param language The language for the results (ISO 639-1 format)
     * @param page The page of results to return, see [DiscoverMovieResponse.totalPages]
     * @param primaryReleaseYear The primary release year, e.g. 1972
     * @param primaryReleaseDateGte Release >= this date, using tmdb date format: yyyy-mm-dd
     * @param primaryReleaseDateLte Release <= this date, using tmdb date format
     * @param region ISO-3166-1 country code (https://en.wikipedia.org/wiki/ISO_3166-1)
     * @param releaseDateGte region-specific release date. If there is none, primary date is used
     * @param releaseDateLte region-specific release date. If there is none, primary date is used
     * @param sortBy how to sort the response list, see [the TMDB docs](https://developer.themoviedb.org/reference/discover-movie)
     * for possible values.
     * @param voteAverageGte vote average >= this value
     * @param voteAverageLte vote average <= this value
     * @param voteCountGte vote count >= this value
     * @param voteCountLte vote count <= this value
     * @param watchRegion The region related to other streaming option params: monetization types
     * and watch providers
     * @param withCast AND/OR list of cast member names, see [the TMDB docs](https://developer.themoviedb.org/reference/discover-movie)
     * for more about AND/OR logic.
     * @param withCompanies AND/OR list of company names
     * @param withCrew AND/OR list of crew member names
     * @param withGenres AND/OR list of genre names
     * @param withKeywords AND/OR list of keywords
     * @param withOriginCountry The movie's original country
     * @param withOriginalLanguage ISO 639-1 format
     * @param withPeople AND/OR list of people. I'm guessing this is combined cast & crew?
     * @param withReleaseType format: AND/OR list of ints 1-6. See [Release Types Guide](https://developer.themoviedb.org/docs/region-support#release-types)
     * for more.
     * @param withRuntimeGte Runtime >=, in minutes
     * @param withRuntimeLte Runtime <=, in minutes
     * @param withWatchMonetizationTypes AND/OR list of streaming buying options (free, rent, etc.)
     * see [the TMDB docs](https://developer.themoviedb.org/reference/discover-movie) for list of possible values.
     * @param withWatchProviders AND/OR list of streaming services - netflix etc.
     * @param withoutCompanies Exclude movies from these companies, probably an AND/OR list
     * @param withoutGenres Exclude movies with these genres, probably an AND/OR list
     * @param withoutKeywords Exclude movies with these keywords, probably an AND/OR list
     * @param withoutWatchProviders Exclude movies on these streaming platforms, probably an AND/OR list
     * @param year The year the movie was made.
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
        @Query("with_origin_country") withOriginCountry: String? = null,
        @Query("with_original_language") withOriginalLanguage: String? = null,
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

    /**
     * Advanced TV show search.
     *
     * @param airDateGte Air date of next episode >=, uses TMDB date format: yyyy-mm-dd
     * @param airDateLte Air date of next episode <=, uses TMDB date format
     * @param firstAirDateYear Air date year of the first episode
     * @param firstAirDateGte First air date >=, uses TMDB date format
     * @param firstAirDateLte First air date <=, uses TMDB date format
     * @param includeAdult include adult tv shows
     * @param includeNullFirstAirDates I guess first air dates can be null idk
     * @param language The language for the results (ISO 639-1 format)
     * @param page The page of results to return, see [DiscoverTvResponse.totalPages]
     * @param screenedTheatrically Whether the show was screened theatrically, or can be null to ignore
     * @param sortBy how to sort the response list, see [the TMDB docs](https://developer.themoviedb.org/reference/discover-tv)
     * for possible values.
     * @param timezone I don't know how this is used
     * @param voteAverageGte vote average >= this value
     * @param voteAverageLte vote average <= this value
     * @param voteCountGte vote count >= this value
     * @param voteCountLte vote count <= this value
     * @param watchRegion The region related to other streaming option params: monetization types
     * and watch providers
     * @param withCompanies AND/OR list of company names
     * @param withGenres AND/OR list of genre names
     * @param withKeywords AND/OR list of keywords
     * @param withNetworks I don't know
     * @param withOriginCountry The movie's original country (ISO 3166-1)
     * @param withOriginalLanguage ISO 639-1 format
     * @param withRuntimeGte Maybe this is total show runtime, in minutes
     * @param withRuntimeLte Maybe this is total show runtime, in minutes
     * @param withStatus Like cancelled, returning, pilot, etc. Format: AND/OR list of ints 0-5.
     * For the status meanings, see [this forum reply](https://www.themoviedb.org/talk/58b1cfbac3a368077800feb5?language=en-CA)
     * @param withWatchMonetizationTypes AND/OR list of streaming buying options (free, rent, etc.)
     * see [the TMDB docs](https://developer.themoviedb.org/reference/discover-movie) for list of possible values.
     * @param withWatchProviders AND/OR list of streaming services - netflix etc.
     * @param withoutCompanies Exclude movies from these companies, probably an AND/OR list
     * @param withoutGenres Exclude movies with these genres, probably an AND/OR list
     * @param withoutKeywords Exclude movies with these keywords, probably an AND/OR list
     * @param withoutWatchProviders Exclude movies on these streaming platforms, probably an AND/OR list
     * @param withType Like miniseries, reality, talk show, etc. Format: AND/OR list of ints 0-6.
     * See [TMDB Bible](https://www.themoviedb.org/bible/tv#59f7403f9251416e7100002a)
     */
    @GET("discover/tv")
    suspend fun discoverTvShows(
        @Query("air_date.gte") airDateGte: String? = null,
        @Query("air_date.lte") airDateLte: String? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
        @Query("first_air_date.gte") firstAirDateGte: String? = null,
        @Query("first_air_date.lte") firstAirDateLte: String? = null,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_null_first_air_dates") includeNullFirstAirDates: Boolean = false,
        // REFACTOR [24-02-11 4:57p.m.] -- hardcoded language string.
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("screened_theatrically") screenedTheatrically: Boolean? = null,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("timezone") timezone: String? = null,
        @Query("vote_average.gte") voteAverageGte: Float? = null,
        @Query("vote_average.lte") voteAverageLte: Float? = null,
        @Query("vote_count.gte") voteCountGte: Float? = null,
        @Query("vote_count.lte") voteCountLte: Float? = null,
        @Query("watch_region") watchRegion: String? = null,
        @Query("with_companies") withCompanies: String? = null,
        @Query("with_genres") withGenres: String? = null,
        @Query("with_keywords") withKeywords: String? = null,
        @Query("with_networks") withNetworks: Int? = null,
        @Query("with_origin_country") withOriginCountry: String? = null,
        @Query("with_original_language") withOriginalLanguage: String? = null,
        @Query("with_runtime.gte") withRuntimeGte: Int? = null,
        @Query("with_runtime.lte") withRuntimeLte: Int? = null,
        @Query("with_status") withStatus: String? = null,
        @Query("with_watch_monetization_types") withWatchMonetizationTypes: String? = null,
        @Query("with_watch_providers") withWatchProviders: String? = null,
        @Query("without_companies") withoutCompanies: String? = null,
        @Query("without_genres") withoutGenres: String? = null,
        @Query("without_keywords") withoutKeywords: String? = null,
        @Query("without_watch_providers") withoutWatchProviders: String? = null,
        @Query("with_type") withType: String? = null
    ): Result<DiscoverTvResponse>
}