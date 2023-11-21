package com.rbraithwaite.untitledmovietracker.data.media

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.media.MediaRepositoryImpl
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argForWhich
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
    fun addNewCustomMediaWithReview_callsDao() = testScope.runTest {
        val mediaTitle = "test"
        val customMedia = CustomMedia(title = mediaTitle)

        val rating = 34
        val review = "review text"
        val watchContext = "watch context"
        val reviewDate = ReviewDate(2022, 10, 10)
        val mediaReview = MediaReview(
            rating, review, reviewDate, watchContext
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