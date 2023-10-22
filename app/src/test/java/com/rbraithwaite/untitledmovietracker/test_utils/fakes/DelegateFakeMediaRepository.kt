package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
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
    mediaDao: FakeMediaDao
): MediaRepository {
    val mock: MediaRepository = mock()

    private val real: MediaRepository = MediaRepositoryImpl(
        externalScope,
        coroutineDispatcher,
        mediaDao
    )

    /**
     * Useful for setup code that you don't want to ruin late verifications.
     */
    var mockEnabled: Boolean = true

    suspend fun withMockEnabled(mockEnabled: Boolean, work: suspend MediaRepository.() -> Unit) {
        this.mockEnabled = mockEnabled
        work()
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

    override suspend fun findMedia(searchCriteria: String): List<Media> {
        if (mockEnabled) {
            mock.findMedia(searchCriteria)
        }
        return real.findMedia(searchCriteria)
    }

    //endregion
}