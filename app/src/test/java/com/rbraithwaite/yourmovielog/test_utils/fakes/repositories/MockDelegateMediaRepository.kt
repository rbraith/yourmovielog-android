package com.rbraithwaite.yourmovielog.test_utils.fakes.repositories

import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.SearchResult
import com.rbraithwaite.yourmovielog.core.repositories.MediaRepository
import com.rbraithwaite.yourmovielog.test_utils.fakes.MockDelegate

class MockDelegateMediaRepository(
    delegate: MediaRepository
): MediaRepository, MockDelegate<MediaRepository>(delegate, MediaRepository::class.java) {
    override suspend fun addMedia(media: Media) {
        suspendFunDelegate<Unit>("addMedia", media)
    }

    override suspend fun searchMulti(query: String): List<SearchResult> {
        return suspendFunDelegate("searchMulti", query)
    }
}