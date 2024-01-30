package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.ReviewRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass
import org.mockito.kotlin.mock

// REFACTOR [24-01-18 11:27p.m.] -- it'd be nice to extract this DelegateFake pattern,
//  maybe another annotation codegen library.
class DelegateFakeReviewRepository(
    reviewDao: FakeReviewDao,
    mediaDao: FakeMediaDao,
    externalScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher,
): ReviewRepository {
    val mock: ReviewRepository = mock()

    private val real: ReviewRepository = ReviewRepositoryImpl(
        reviewDao,
        mediaDao,
        externalScope,
        coroutineDispatcher
    )

    /**
     * Useful for setup code that you don't want to ruin late verifications.
     */
    var mockEnabled: Boolean = true

    suspend fun withMockEnabled(mockEnabled: Boolean, work: suspend ReviewRepository.() -> Unit) {
        val oldMockEnabled = this.mockEnabled
        this.mockEnabled = mockEnabled
        work()
        this.mockEnabled = oldMockEnabled
    }

    override suspend fun getAllReviews(extras: Set<KClass<out MediaReview.Extras>>): List<MediaReview> {
        if (mockEnabled) {
            mock.getAllReviews(extras)
        }
        return real.getAllReviews(extras)
    }

    override suspend fun addReviewForCustomMedia(review: MediaReview, customMediaId: Long) {
        if (mockEnabled) {
            mock.addReviewForCustomMedia(review, customMediaId)
        }
        real.addReviewForCustomMedia(review, customMediaId)
    }
}