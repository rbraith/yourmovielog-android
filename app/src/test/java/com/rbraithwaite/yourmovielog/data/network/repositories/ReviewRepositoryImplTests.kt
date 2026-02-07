package com.rbraithwaite.yourmovielog.data.network.repositories

import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import com.rbraithwaite.yourmovielog.data.database.dao.ReviewDao
import com.rbraithwaite.yourmovielog.data.repositories.ReviewRepositoryImpl
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aReview
import com.rbraithwaite.yourmovielog.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.yourmovielog.test_utils.test_bases.InMemoryDatabaseTests
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class ReviewRepositoryImplTests : InMemoryDatabaseTests() {
    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var reviewDao: ReviewDao
    private lateinit var reviewRepository: ReviewRepository

    override fun onDatabaseCreated() {
        reviewDao = database.reviewDao()
        reviewRepository = ReviewRepositoryImpl(
            reviewDao,
            mainDispatcherRule.testScope,
            mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun getAllReviewsTest() = runTest {
        // GIVEN some reviews are added to the repository
        // ------------------------------------------
        reviewRepository.addReview(
            aReview().withUuid(UUID(1L, 1L)).build(),
            UUID(2L, 1L)
        )
        reviewRepository.addReview(
            aReview().withUuid(UUID(1L, 2L)).build(),
            UUID(2L, 2L)
        )

        // WHEN getAllReviews is called
        // ------------------------------------------
        val allReviews = reviewRepository.getAllReviews()

        // THEN all the reviews are returned
        // ------------------------------------------
        assert(allReviews.size == 2)
    }
}
