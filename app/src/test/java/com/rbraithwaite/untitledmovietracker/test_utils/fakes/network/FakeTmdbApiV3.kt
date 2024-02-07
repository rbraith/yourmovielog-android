package com.rbraithwaite.untitledmovietracker.test_utils.fakes.network

import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
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
}