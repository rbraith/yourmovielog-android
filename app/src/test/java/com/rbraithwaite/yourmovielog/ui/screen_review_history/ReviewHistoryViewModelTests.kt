package com.rbraithwaite.yourmovielog.ui.screen_review_history

import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aReview
import com.rbraithwaite.yourmovielog.test_utils.fakes.repositories.FakeReviewRepository
import com.rbraithwaite.yourmovielog.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.yourmovielog.ui.screens.review_history.ReviewHistoryUiState
import com.rbraithwaite.yourmovielog.ui.screens.review_history.ReviewHistoryViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class ReviewHistoryViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val fakeReviewRepository = FakeReviewRepository()

    private val viewModel: ReviewHistoryViewModel by lazy {
        ReviewHistoryViewModel(fakeReviewRepository)
    }

    @Test
    fun initialUiStateIsLoading() = runTest {
        assert(viewModel.uiState.value is ReviewHistoryUiState.Loading)
    }

    @Test
    fun viewModelLoadsReviews() = runTest {
        // GIVEN a review repository containing some reviews
        // ------------------------------------------
        fakeReviewRepository.setReviews(
            aReview().withUuid(UUID(1L, 1L)),
            aReview().withUuid(UUID(1L, 2L)),
            aReview().withUuid(UUID(1L, 3L)),
        )

        // WHEN the ReviewHistoryViewModel is first created
        // ------------------------------------------
        viewModel.toString() // janky, just want to trigger the viewmodel being lazily created lol
        mainDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        val uiState = viewModel.uiState.value

        // THEN it loads those reviews for the ui state
        // ------------------------------------------
        val successState = uiState as ReviewHistoryUiState.Success
        assert(successState.reviews.size == 3)
    }
}
