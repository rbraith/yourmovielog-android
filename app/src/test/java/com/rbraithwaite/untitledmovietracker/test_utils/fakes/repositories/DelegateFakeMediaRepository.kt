package com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories

import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.MediaRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.network.FakeTmdbApiV3
import kotlinx.coroutines.CoroutineDispatcher
import org.mockito.kotlin.mock

class DelegateFakeMediaRepository(
    dispatcher: CoroutineDispatcher,
    tmdbApiV3: FakeTmdbApiV3
): MediaRepository {
    val mock: MediaRepository = mock()

    private val real: MediaRepository = MediaRepositoryImpl(dispatcher, tmdbApiV3)

    /**
     * Useful for setup code that you don't want to ruin late verifications.
     */
    var mockEnabled: Boolean = true

    override suspend fun searchMulti(query: String): List<SearchResult> {
        if (mockEnabled) {
            mock.searchMulti(query)
        }
        return real.searchMulti(query)
    }
}