package com.rbraithwaite.yourmovielog.data.network.repositories

import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import com.rbraithwaite.yourmovielog.data.database.dao.MediaDao
import com.rbraithwaite.yourmovielog.data.database.dao.ReviewDao
import com.rbraithwaite.yourmovielog.data.repositories.ReviewRepositoryImpl
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aReview
import com.rbraithwaite.yourmovielog.test_utils.data_builders.database_entities.aMediaMovieEntity
import com.rbraithwaite.yourmovielog.test_utils.data_builders.database_entities.aMediaReviewEntity
import com.rbraithwaite.yourmovielog.test_utils.data_builders.database_entities.aMediaTvShowEntity
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
    private lateinit var mediaDao: MediaDao
    private lateinit var reviewRepository: ReviewRepository

    override fun onDatabaseCreated() {
        reviewDao = database.reviewDao()
        mediaDao = database.mediaDao()
        reviewRepository = ReviewRepositoryImpl(
            reviewDao,
            mediaDao,
            mainDispatcherRule.testScope,
            mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun getAllReviewsTest() = runTest {
        // GIVEN some reviews are added to the repository
        // ------------------------------------------
        reviewRepository.addReview(
            aReview().withUuid(UUID(1L, 1L)).build()
        )
        reviewRepository.addReview(
            aReview().withUuid(UUID(1L, 2L)).build()
        )

        // WHEN getAllReviews is called
        // ------------------------------------------
        val allReviews = reviewRepository.getAllReviews()

        // THEN all the reviews are returned
        // ------------------------------------------
        assert(allReviews.size == 2)
    }

    @Test
    fun getAllReviewsWithMediaTest() = runTest {
        // GIVEN some persisted media and reviews
        // ------------------------------------------
        val movieUuid = UUID(1L, 1L)
        val reviewUuid1 = UUID(2L, 1L)
        mediaDao.insertMovie(aMediaMovieEntity().withUuid(movieUuid).build())
        reviewDao.insertReview(aMediaReviewEntity().withUuid(reviewUuid1).withMediaUuid(movieUuid).build())

        val tvShowUuid = UUID(1L, 2L)
        val reviewUuid2 = UUID(2L, 2L)
        mediaDao.insertTvShow(aMediaTvShowEntity().withUuid(tvShowUuid).build())
        reviewDao.insertReview(aMediaReviewEntity().withUuid(reviewUuid2).withMediaUuid(tvShowUuid).build())

        // WHEN getAllReviewsWithMedia is called
        // ------------------------------------------
        val result = reviewRepository.getAllReviewsWithMedia()

        // THEN the correct combined reviews with media are returned
        // ------------------------------------------
        assert(result.size == 2)
        assert(result.firstOrNull { it.value.uuid == reviewUuid1 && it.media.uuid == movieUuid } != null)
        assert(result.firstOrNull { it.value.uuid == reviewUuid2 && it.media.uuid == tvShowUuid } != null)
    }
}
