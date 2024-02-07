package com.rbraithwaite.untitledmovietracker.ui.screens.review_history

import com.rbraithwaite.untitledmovieapp.ui.screens.review_history.ReviewHistoryUiState
import com.rbraithwaite.untitledmovieapp.ui.screens.review_history.ReviewHistoryViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ReviewHistoryViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(
        testDispatcher = StandardTestDispatcher()
    )

    private val testDependencyManager = TestDependencyManager(
        mainDispatcherRule.testScope,
        mainDispatcherRule.testDispatcher
    )

    private val viewModel by lazy {
        ReviewHistoryViewModel(
            testDependencyManager.reviewRepository
        )
    }

    @Test
    fun uiStateStartsAsLoading() {
        // GIVEN a ReviewHistoryViewModel
        // -------------------------------------------

        // n/a

        // WHEN uiState is first accessed
        // -------------------------------------------

        val uiState = viewModel.uiState.value

        // THEN it will be ReviewHistoryUiState.Loading
        // -------------------------------------------

        assert(uiState is ReviewHistoryUiState.Loading)
    }

    @Test
    fun viewModelLoadsReviewsWhenInstantiated() = mainDispatcherRule.testScope.runTest {
        // TODO [24-02-2 12:10a.m.] broken.
//        // GIVEN a database with some reviews
//        // -------------------------------------------
//
//        val expectedCustomMediaId = 1L
//        val expectedTmdbMovieId = 123L
//
//        testDependencyManager.initializeTestState {
//            withCustomMedia(aCustomMedia())
//            withMediaReviewsForCustomMedia(mediaReview() to expectedCustomMediaId)
//
//            withTmdbLiteMovies(tmdbLiteMovie().withId(expectedTmdbMovieId))
//            withMediaReviewsForTmdbMovie(mediaReview() to expectedTmdbMovieId)
//        }
//
//        // WHEN the ReviewHistoryViewModel is instantiated
//        // -------------------------------------------
//
//        val uiState = viewModel.uiState
//
//        this.testScheduler.advanceUntilIdle()
//
//        // THEN eventually the reviews are loaded
//        // -------------------------------------------
//
//        val successState = uiState.value as ReviewHistoryUiState.Success
//        assertThat(successState.reviews.size, willBe(2))
//
//        val reviews = successState.reviews
//        val firstReview = reviews.first()
//        val firstRelatedMedia = firstReview.getExtra<Review.Extras.RelatedMedia>()
//        val firstCustomMedia = firstRelatedMedia as Review.Extras.RelatedMedia.Custom
//        assert(firstCustomMedia.data.id == expectedCustomMediaId)
//
//        val secondReview = reviews[1]
//        val secondRelatedMedia = secondReview.getExtra<Review.Extras.RelatedMedia>()
//        val firstTmdbMovie = (secondRelatedMedia as Review.Extras.RelatedMedia.Tmdb).data as TmdbLite.Movie
//        assert(firstTmdbMovie.id == expectedTmdbMovieId)
    }
}