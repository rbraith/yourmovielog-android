package com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories

import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.MediaRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.MockDelegate
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.network.FakeTmdbApiV3
import kotlinx.coroutines.CoroutineDispatcher
import org.mockito.kotlin.mock

class MockDelegateMediaRepository(
    delegate: MediaRepository
): MediaRepository, MockDelegate<MediaRepository>(delegate, MediaRepository::class.java) {
    override suspend fun searchMulti(query: String): List<SearchResult> {
        return delegate("searchMulti", query)
    }
}