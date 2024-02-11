package com.rbraithwaite.untitledmovietracker.test_utils.fakes.network

import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.CertificationsResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyDetails
import com.rbraithwaite.untitledmovieapp.data.network.models.CompanyLogos
import com.rbraithwaite.untitledmovieapp.data.network.models.Configuration
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.CountryTimezones
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverMovieResponse
import com.rbraithwaite.untitledmovieapp.data.network.models.JobsConfig
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResults
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeDatabase

class FakeTmdbApiV3(
    private val backend: FakeDatabase
): TmdbApiV3 {
    override suspend fun searchMulti(
        query: String,
        includeAdult: Boolean,
        language: String,
        pageNumber: Int
    ): Result<SearchMultiResults> {
        // TODO [23-11-20 10:51p.m.] -- this only implements using the 'query' arg at the moment.

        val movies = backend.find<SearchMultiResult.Movie> {
            // TODO [23-11-20 10:50p.m.] -- I don't know if this matching logic matches Tmdb.
            title.contains(query)
        }

        val tvShows = backend.find<SearchMultiResult.TvShow> {
            // TODO [23-11-20 10:50p.m.] -- I don't know if this matching logic matches Tmdb.
            name.contains(query)
        }

        val people = backend.find<SearchMultiResult.Person> {
            // TODO [23-11-20 10:50p.m.] -- I don't know if this matching logic matches Tmdb.
            name.contains(query)
        }

        val results: List<SearchMultiResult> = buildList {
            addAll(movies)
            addAll(tvShows)
            addAll(people)
        }

        val searchMultiResult = SearchMultiResults(
            page = 1,
            results = results,
            totalPages = 1,
            totalResults = results.size
        )

        return Result.success(searchMultiResult)
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
}