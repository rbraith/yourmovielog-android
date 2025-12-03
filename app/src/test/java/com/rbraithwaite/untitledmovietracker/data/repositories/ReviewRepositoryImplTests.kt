package com.rbraithwaite.untitledmovietracker.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.ReviewRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.asVarArg
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.core_data.aReview
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.aCustomMovieEntity
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.aReviewEntity
import com.rbraithwaite.untitledmovietracker.test_utils.equalsReview
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

class ReviewRepositoryImplTests {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val testDependencyManager = TestDependencyManager(testScope, testDispatcher)

    private val reviewDao = testDependencyManager.reviewDao
    private val tmdbDao = testDependencyManager.tmdbDao
    private val customMediaDao = testDependencyManager.customMediaDao

    private val reviewRepository = ReviewRepositoryImpl(
        reviewDao,
        tmdbDao,
        customMediaDao,
        testScope,
        testDispatcher
    )

    @Test
    fun upsertReviews_test() = testScope.runTest {
        // GIVEN a set of reviews
        // -------------------------------------------

        val expectedUpdatedReviewId = 123L

        val newReview = aReview().build()
        val updatedReview = aReview().withId(expectedUpdatedReviewId).build()

        // WHEN they are sent to ReviewRepositoryImpl.upsertReviews()
        // -------------------------------------------

        reviewRepository.upsertReviews(
            newReview,
            updatedReview
        )

        // THEN ReviewDao.upsertReviews() is called with the right entities
        // -------------------------------------------

        argumentCaptor<ReviewEntity>().run {
            verify(reviewDao.mock).upsertReviews(capture())

            val varArgs = allValues.first().asVarArg()

            assertThat(varArgs.size, willBe(2))
            assert(varArgs[0].equalsReview(newReview))
            assert(varArgs[1].equalsReview(updatedReview))
        }
    }
}