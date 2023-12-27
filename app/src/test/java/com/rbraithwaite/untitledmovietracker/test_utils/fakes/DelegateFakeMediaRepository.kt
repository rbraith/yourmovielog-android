package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.media.MediaRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.CustomMediaBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.listValuesOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.mockito.kotlin.mock

class DelegateFakeMediaRepository(
    externalScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher,
    mediaDao: FakeMediaDao,
    tmdbApiV3: FakeTmdbApiV3
): MediaRepository {
    val mock: MediaRepository = mock()

    private val real: MediaRepository = MediaRepositoryImpl(
        externalScope,
        coroutineDispatcher,
        mediaDao,
        tmdbApiV3
    )

    /**
     * Useful for setup code that you don't want to ruin late verifications.
     */
    var mockEnabled: Boolean = true

    suspend fun withMockEnabled(mockEnabled: Boolean, work: suspend MediaRepository.() -> Unit) {
        this.mockEnabled = mockEnabled
        work()
        // BUG [23-11-19 4:38p.m.] -- this inversion is a bug, instead should cache old mockEnabled
        //  value and set back to that.
        this.mockEnabled = !mockEnabled
    }

    // *********************************************************
    // MediaRepository
    // *********************************************************
    //region MediaRepository

    override suspend fun addNewCustomMedia(customMedia: CustomMedia) {
        if (mockEnabled) {
            mock.addNewCustomMedia(customMedia)
        }
        real.addNewCustomMedia(customMedia)
    }

    override suspend fun addNewCustomMediaWithReview(
        customMedia: CustomMedia,
        review: MediaReview
    ) {
        if (mockEnabled) {
            mock.addNewCustomMediaWithReview(customMedia, review)
        }
        real.addNewCustomMediaWithReview(customMedia, review)
    }

    override suspend fun addTmdbMovieReview(tmdbMovieId: Int, review: MediaReview) {
        TODO("Not yet implemented")
    }

    override suspend fun findMedia(searchCriteria: String): List<SearchResult> {
        if (mockEnabled) {
            mock.findMedia(searchCriteria)
        }
        return real.findMedia(searchCriteria)
    }

    //endregion
}