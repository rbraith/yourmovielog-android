package com.rbraithwaite.untitledmovieapp.data.network

import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResults
import org.intellij.lang.annotations.Language
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
    ): SearchMultiResults
}