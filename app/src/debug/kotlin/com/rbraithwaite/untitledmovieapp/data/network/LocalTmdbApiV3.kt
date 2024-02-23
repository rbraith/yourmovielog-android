package com.rbraithwaite.untitledmovieapp.data.network

import com.google.gson.Gson
import com.rbraithwaite.untitledmovieapp.DebugUtils
import com.rbraithwaite.untitledmovieapp.data.network.models.CertificationsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyDetails
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyLogos
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanySearchResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.Configuration
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryTimezones
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverMovieResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverTvResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.Genres
import com.rbraithwaite.untitledmovieapp.data.network.models.JobsConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.MovieDetailsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.MovieSearchResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.PersonDetailsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.PersonSearchResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.PopularPeopleResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.TvShowSearchResponse
import timber.log.Timber

class LocalTmdbApiV3(
    private val gson: Gson
): TmdbApiV3 {
    override suspend fun getTrendingAll(
        timeWindow: String,
        language: String
    ): Result<SearchMultiResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingMovies(
        timeWindow: String,
        language: String
    ): Result<MovieSearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingTvShows(
        timeWindow: String,
        language: String
    ): Result<TvShowSearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingPeople(
        timeWindow: String,
        language: String
    ): Result<PersonSearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchTvShows(
        query: String,
        firstAirDateYear: Int?,
        includeAdult: Boolean,
        language: String,
        page: Int,
        year: Int?
    ): Result<TvShowSearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchPeople(
        query: String,
        includeAdult: Boolean,
        language: String,
        page: Int
    ): Result<PersonSearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(
        query: String,
        includeAdult: Boolean,
        language: String,
        primaryReleaseYear: String?,
        page: Int,
        region: String?,
        year: String?
    ): Result<MovieSearchResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMulti(
        query: String,
        includeAdult: Boolean,
        language: String,
        pageNumber: Int
    ): Result<SearchMultiResponse> {
        Timber.d("searchMulti: $query")

        // Just returning a static response no matter the request, for now
        val responseString = DebugUtils.loadResourceFileAsString("search-multi-response.json")
        val searchMultiResponse = gson.fromJson(responseString, SearchMultiResponse::class.java)
        return Result.success(searchMultiResponse)
    }

    override suspend fun getMovieCertifications(): Result<CertificationsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvCertifications(): Result<CertificationsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getConfiguration(): Result<Configuration> {
        TODO("Not yet implemented")
    }

    override suspend fun getCountryConfiguration(): Result<List<CountryConfig>> {
        TODO("Not yet implemented")
    }

    override suspend fun getJobsConfiguration(): Result<List<JobsConfig>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTimezonesConfiguration(): Result<List<CountryTimezones>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCompanyDetails(companyId: Long): Result<CompanyDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun getCompanyLogos(companyId: Long): Result<CompanyLogos> {
        TODO("Not yet implemented")
    }

    override suspend fun discoverMovies(
        certification: String?,
        certificationGte: String?,
        certificationLte: String?,
        certificationCountry: String?,
        includeAdult: Boolean,
        includeVideo: Boolean,
        language: String,
        page: Int,
        primaryReleaseYear: Int?,
        primaryReleaseDateGte: String?,
        primaryReleaseDateLte: String?,
        region: String?,
        releaseDateGte: String?,
        releaseDateLte: String?,
        sortBy: String,
        voteAverageGte: Float?,
        voteAverageLte: Float?,
        voteCountGte: Float?,
        voteCountLte: Float?,
        watchRegion: String?,
        withCast: String?,
        withCompanies: String?,
        withCrew: String?,
        withGenres: String?,
        withKeywords: String?,
        withOriginalCountry: String?,
        withOriginLanguage: String?,
        withPeople: String?,
        withReleaseType: String?,
        withRuntimeGte: Int?,
        withRuntimeLte: Int?,
        withWatchMonetizationTypes: String?,
        withWatchProviders: String?,
        withoutCompanies: String?,
        withoutGenres: String?,
        withoutKeywords: String?,
        withoutWatchProviders: String?,
        year: Int?
    ): Result<DiscoverMovieResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun discoverTvShows(
        airDateGte: String?,
        airDateLte: String?,
        firstAirDateYear: Int?,
        firstAirDateGte: String?,
        firstAirDateLte: String?,
        includeAdult: Boolean,
        includeNullFirstAirDates: Boolean,
        language: String,
        page: Int,
        screenedTheatrically: Boolean?,
        sortBy: String,
        timezone: String?,
        voteAverageGte: Float?,
        voteAverageLte: Float?,
        voteCountGte: Float?,
        voteCountLte: Float?,
        watchRegion: String?,
        withCompanies: String?,
        withGenres: String?,
        withKeywords: String?,
        withNetworks: Int?,
        withOriginCountry: String?,
        withOriginalLanguage: String?,
        withRuntimeGte: Int?,
        withRuntimeLte: Int?,
        withStatus: String?,
        withWatchMonetizationTypes: String?,
        withWatchProviders: String?,
        withoutCompanies: String?,
        withoutGenres: String?,
        withoutKeywords: String?,
        withoutWatchProviders: String?,
        withType: String?
    ): Result<DiscoverTvResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieGenres(): Result<Genres> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvGenres(): Result<Genres> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularMovies(
        language: String,
        page: Int,
        region: String?
    ): Result<DiscoverMovieResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(
        language: String,
        page: Int,
        region: String?
    ): Result<DiscoverMovieResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowsAiringToday(
        language: String,
        page: Int,
        timezone: String?
    ): Result<DiscoverTvResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowsOnTheAir(
        language: String,
        page: Int,
        timezone: String?
    ): Result<DiscoverTvResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularTvShows(
        language: String,
        page: Int
    ): Result<DiscoverTvResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedTvShow(
        language: String,
        page: Int
    ): Result<DiscoverTvResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetails(
        movieId: Long,
        language: String,
        appendToResponse: String?
    ): Result<MovieDetailsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPopularPeople(
        language: String,
        page: Int
    ): Result<PopularPeopleResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonDetails(
        personId: Long,
        language: String,
        appendToResponse: String?
    ): Result<PersonDetailsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getNowPlayingMovies(
        language: String,
        page: Int,
        region: String?
    ): Result<DiscoverMovieResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(
        language: String,
        page: Int,
        region: String?
    ): Result<DiscoverMovieResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchCompanies(query: String, page: Int): Result<CompanySearchResponse> {
        TODO("Not yet implemented")
    }
}