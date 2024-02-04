package com.rbraithwaite.untitledmovietracker.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.CustomMediaRepositoryImpl
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.asVarArg
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.aCustomMovie
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.aCustomMovieEntity
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argForWhich
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.check
import org.mockito.kotlin.verify
import kotlin.math.exp

class CustomMediaRepositoryImplTests {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val testDependencyManager = TestDependencyManager(testScope, testDispatcher)

    private val customMediaDao = testDependencyManager.customMediaDao

    private val customMediaRepository = CustomMediaRepositoryImpl(
        testScope,
        testDispatcher,
        customMediaDao
    )

    @Test
    fun addingCustomMovie() = testScope.runTest {
        // GIVEN a new custom movie
        // -------------------------------------------

        val expectedTitle = "addingCustomMovie"

        val customMovie = aCustomMovie()
            .asNewMovie()
            .withTitle(expectedTitle)
            .build()

        // WHEN that custom movie is added to the repository
        // -------------------------------------------

        customMediaRepository.addOrUpdateCustomMedia(customMovie)

        // THEN a correct entity is added to the custom media dao
        // -------------------------------------------

        argumentCaptor<CustomMovieEntity>().apply {
            verify(customMediaDao.mock).insertOrUpdateCustomMovies(capture())

            assertThat(allValues.size, willBe(1))

            val entity = allValues.first().asVarArg()[0]
            assertThat(entity.title, willBe(expectedTitle))
        }
    }

    @Test
    fun findMedia_test() = testScope.runTest {
        // GIVEN several saved custom media
        // -------------------------------------------

        val titleExpected = "what the"

        testDependencyManager.initializeDatabaseState {
            withCustomMovies(
                aCustomMovieEntity().withTitle(titleExpected),
                aCustomMovieEntity().withTitle("skip this title")
            )
        }

        // WHEN findMedia is run
        // -------------------------------------------

        val foundMedia = customMediaRepository.findMedia("what")

        // THEN the correct media is found
        // -------------------------------------------

        assertThat(foundMedia.size, willBe(1))

        val foundMovie = foundMedia.first() as CustomMedia.Movie
        assertThat(foundMovie.title, willBe(titleExpected))
    }

    @Test
    fun addTmdbMovieReview_test() = testScope.runTest {
        // TODO [24-02-2 12:01a.m.] broken.
//        // GIVEN a MediaReview
//        // -------------------------------------------
//
//        val rating = 34
//        val review = "review text"
//        val watchContext = "watch context"
//        val reviewDate = ReviewDate(2022, 10, 10)
//        val mediaReview = Review(
//            0, rating, review, reviewDate, watchContext
//        )
//
//        // WHEN that review is added for a TmdbMovie
//        // -------------------------------------------
//
//        val expectedTmdbMovieId = 123L
//
//        mediaRepositoryImpl.addTmdbMovieReview(
//            expectedTmdbMovieId,
//            mediaReview
//        )
//
//        // THEN the dao is called with proper arguments
//        // -------------------------------------------
//
//        verify(mockTmdbDao).addReview(check { mediaReviewEntity ->
//            assertThat("", mediaReviewEntity.id == 0L)
//            assertThat("", mediaReviewEntity.mediaId == expectedTmdbMovieId.toLong())
//            // REFACTOR [23-12-27 2:14p.m.] -- hardcoded string.
//            assertThat("", mediaReviewEntity.mediaType == "tmdb_movie")
//            assertThat("", mediaReviewEntity.rating == rating)
//            assertThat("", mediaReviewEntity.review == review)
//            assertThat("", mediaReviewEntity.reviewDate == reviewDate)
//            assertThat("", mediaReviewEntity.watchContext == watchContext)
//        })
    }

    @Test
    fun addNewCustomMediaWithReview_callsDao() = testScope.runTest {
        // TODO [24-02-2 12:02a.m.] broken.
//        val mediaTitle = "test"
//        val customMovie = CustomMovie(title = mediaTitle)
//
//        val rating = 34
//        val review = "review text"
//        val watchContext = "watch context"
//        val reviewDate = ReviewDate(2022, 10, 10)
//        val mediaReview = Review(
//            0, rating, review, reviewDate, watchContext
//        )
//
//        mediaRepositoryImpl.addNewCustomMediaWithReview(
//            customMovie,
//            mediaReview
//        )
//
//        val expectedCustomMovieEntity = CustomMovieEntity(0, mediaTitle)
//        val expectedReviewEntity = ReviewEntity(
//            0, -1, "custom", rating, review, reviewDate, watchContext
//        )
//
//        verify(mockTmdbDao).addNewCustomMediaWithReview(
//            argForWhich { equals(expectedCustomMovieEntity) },
//            argForWhich { equals(expectedReviewEntity) }
//        )
    }
}