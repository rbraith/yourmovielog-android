package com.rbraithwaite.untitledmovietracker.ui.screens.review_history

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.ui.screens.review_history.ReviewHistoryUiState
import com.rbraithwaite.untitledmovieapp.ui.screens.review_history.ReviewHistoryViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.aCustomMedia
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.mediaReview
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
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
        // GIVEN a database with some reviews
        // -------------------------------------------

        val expectedCustomMediaId = 1L

        testDependencyManager.initializeTestState {
            withCustomMedia(aCustomMedia())
            withMediaReviewsForCustomMedia(mediaReview() to expectedCustomMediaId)
        }

        // WHEN the ReviewHistoryViewModel is instantiated
        // -------------------------------------------

        val uiState = viewModel.uiState

        this.testScheduler.advanceUntilIdle()

        // THEN eventually the reviews are loaded
        // -------------------------------------------

        val successState = uiState.value as ReviewHistoryUiState.Success
        assertThat(successState.reviews.size, willBe(1))

        val review = successState.reviews.first()
        val relatedMedia = review.getExtra<MediaReview.Extras.RelatedMedia>()
        val customRelated = relatedMedia as MediaReview.Extras.RelatedMedia.Custom
        assert(customRelated.data.id == expectedCustomMediaId)
    }
}