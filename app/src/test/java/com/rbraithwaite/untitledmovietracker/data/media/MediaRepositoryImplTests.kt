package com.rbraithwaite.untitledmovietracker.data.media

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.MediaRepositoryImpl
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argForWhich
import org.mockito.kotlin.check
import org.mockito.kotlin.verify

class MediaRepositoryImplTests {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val mockMediaDao = mock(MediaDao::class.java)

    private val mockTmdbApiV3 = mock(TmdbApiV3::class.java)

    private val mediaRepositoryImpl = MediaRepositoryImpl(
        testScope,
        testDispatcher,
        mockMediaDao,
        mockTmdbApiV3
    )

    @Test
    fun addTmdbMovieReview_test() = testScope.runTest {
        // GIVEN a MediaReview
        // -------------------------------------------

        val rating = 34
        val review = "review text"
        val watchContext = "watch context"
        val reviewDate = ReviewDate(2022, 10, 10)
        val mediaReview = MediaReview(
            0, rating, review, reviewDate, watchContext
        )

        // WHEN that review is added for a TmdbMovie
        // -------------------------------------------

        val expectedTmdbMovieId = 123L

        mediaRepositoryImpl.addTmdbMovieReview(
            expectedTmdbMovieId,
            mediaReview
        )

        // THEN the dao is called with proper arguments
        // -------------------------------------------

        verify(mockMediaDao).addReview(check { mediaReviewEntity ->
            assertThat("", mediaReviewEntity.id == 0L)
            assertThat("", mediaReviewEntity.mediaId == expectedTmdbMovieId.toLong())
            // REFACTOR [23-12-27 2:14p.m.] -- hardcoded string.
            assertThat("", mediaReviewEntity.mediaType == "tmdb_movie")
            assertThat("", mediaReviewEntity.rating == rating)
            assertThat("", mediaReviewEntity.review == review)
            assertThat("", mediaReviewEntity.reviewDate == reviewDate)
            assertThat("", mediaReviewEntity.watchContext == watchContext)
        })
    }

    @Test
    fun addNewCustomMediaWithReview_callsDao() = testScope.runTest {
        val mediaTitle = "test"
        val customMedia = CustomMedia(title = mediaTitle)

        val rating = 34
        val review = "review text"
        val watchContext = "watch context"
        val reviewDate = ReviewDate(2022, 10, 10)
        val mediaReview = MediaReview(
            0, rating, review, reviewDate, watchContext
        )

        mediaRepositoryImpl.addNewCustomMediaWithReview(
            customMedia,
            mediaReview
        )

        val expectedCustomMediaEntity = CustomMediaEntity(0, mediaTitle)
        val expectedMediaReviewEntity = MediaReviewEntity(
            0, -1, "custom", rating, review, reviewDate, watchContext
        )

        verify(mockMediaDao).addNewCustomMediaWithReview(
            argForWhich { equals(expectedCustomMediaEntity) },
            argForWhich { equals(expectedMediaReviewEntity) }
        )
    }
}