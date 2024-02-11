package com.rbraithwaite.untitledmovieapp.data.network

import com.google.gson.Gson
import com.rbraithwaite.untitledmovieapp.DebugUtils
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
import timber.log.Timber

class LocalTmdbApiV3(
    private val gson: Gson
): TmdbApiV3 {
    override suspend fun searchMulti(
        query: String,
        includeAdult: Boolean,
        language: String,
        pageNumber: Int
    ): Result<SearchMultiResults> {
        Timber.d("searchMulti: $query")

        // Just returning a static response no matter the request, for now
        val responseString = DebugUtils.loadResourceFileAsString("search-multi-response.json")
        val searchMultiResults = gson.fromJson(responseString, SearchMultiResults::class.java)
        return Result.success(searchMultiResults)
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
}