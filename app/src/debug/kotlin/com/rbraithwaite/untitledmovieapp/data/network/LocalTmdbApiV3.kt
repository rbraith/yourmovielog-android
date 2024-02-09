package com.rbraithwaite.untitledmovieapp.data.network

import com.google.gson.Gson
import com.rbraithwaite.untitledmovieapp.DebugUtils
import com.rbraithwaite.untitledmovieapp.data.network.models.CertificationsResponse
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
}