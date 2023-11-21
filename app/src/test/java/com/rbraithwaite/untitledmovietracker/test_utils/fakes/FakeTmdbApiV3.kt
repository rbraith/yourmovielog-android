package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResultMovie
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResultPerson
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResultTv
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResults

class FakeTmdbApiV3(
    private val backend: FakeDatabase
): TmdbApiV3 {
    override suspend fun searchMulti(
        query: String,
        includeAdult: Boolean,
        language: String,
        pageNumber: Int
    ): SearchMultiResults {
        // TODO [23-11-20 10:51p.m.] -- this only implements using the 'query' arg at the moment.

        val movies = backend.find<SearchMultiResultMovie> {
            // TODO [23-11-20 10:50p.m.] -- I don't know if this matching logic matches Tmdb.
            title.contains(query)
        }

        val tvShows = backend.find<SearchMultiResultTv> {
            // TODO [23-11-20 10:50p.m.] -- I don't know if this matching logic matches Tmdb.
            name.contains(query)
        }

        val people = backend.find<SearchMultiResultPerson> {
            // TODO [23-11-20 10:50p.m.] -- I don't know if this matching logic matches Tmdb.
            name.contains(query)
        }

        val results: List<SearchMultiResult> = buildList {
            addAll(movies)
            addAll(tvShows)
            addAll(people)
        }

        return SearchMultiResults(
            page = 1,
            results = results,
            totalPages = 1,
            totalResults = results.size
        )
    }
}