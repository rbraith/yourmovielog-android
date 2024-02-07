package com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories

import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.ReviewRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeTmdbDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeCustomMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeReviewDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass
import org.mockito.kotlin.mock

// REFACTOR [24-01-18 11:27p.m.] -- it'd be nice to extract this DelegateFake pattern,
//  maybe another annotation codegen library.
class DelegateFakeReviewRepository(
    reviewDao: FakeReviewDao,
    tmdbDao: FakeTmdbDao,
    customMediaDao: FakeCustomMediaDao,
    externalScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher,
): ReviewRepository {
    val mock: ReviewRepository = mock()

    private val real: ReviewRepository = ReviewRepositoryImpl(
        reviewDao,
        tmdbDao,
        customMediaDao,
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

    override suspend fun getAllReviews(extras: Set<KClass<out Review.Extras>>): List<Review> {
        if (mockEnabled) {
            mock.getAllReviews(extras)
        }
        return real.getAllReviews(extras)
    }

    override suspend fun upsertReviews(vararg reviews: Review) {
        if (mockEnabled) {
            mock.upsertReviews(*reviews)
        }
        real.upsertReviews(*reviews)
    }
}