package com.rbraithwaite.untitledmovietracker.ui.screen_new_review

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.CustomMediaUiState
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
import com.rbraithwaite.untitledmovietracker.test_utils.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import com.rbraithwaite.untitledmovietracker.test_utils.willBeEqualTo
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argForWhich
import org.mockito.kotlin.verify

class NewReviewViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockMediaRepository: MediaRepository = mock(MediaRepository::class.java)

    private val viewModel = NewReviewViewModel(mockMediaRepository)

    @Test
    fun beforeInit_uiStateIsNull() = runTest {
        val uiState = viewModel.uiState.value

        assertThat(uiState, willBe(null))
    }

    @Test
    fun afterInit_usingNewCustomMediaSearchResult() {
        val expectedMediaTitle = "test"
        val searchResult = NewReviewSearchResult.NewCustomMedia(expectedMediaTitle)

        val expectedMedia = CustomMedia(title = expectedMediaTitle)
        val blankReview = MediaReview()

        viewModel.init(searchResult)

        val uiState = viewModel.uiState.value

        assertThat("", uiState?.mediaUiState is CustomMediaUiState)

        val customMediaUiState = uiState?.mediaUiState as CustomMediaUiState
        assertThat(customMediaUiState.media, willBeEqualTo(expectedMedia))
        assertThat(customMediaUiState.isTitleEditable, willBe(true))
        assertThat(uiState.review, willBeEqualTo(blankReview))
    }

    @Test
    fun uiStateIsNull_ifInitWithNull() {
        viewModel.init(null)

        assertThat(viewModel.uiState.value, willBe(null))
    }

    @Test
    fun onConfirmReview_forNewCustomMedia() = runTest {
        val initialTitle = "initial title"
        val searchResult = NewReviewSearchResult.NewCustomMedia(initialTitle)

        viewModel.init(searchResult)
        val uiState = viewModel.uiState

        val updatedTitle = "updated title"
        (uiState.value?.mediaUiState as CustomMediaUiState).editTitle(updatedTitle)
        assertThat((uiState.value?.mediaUiState as CustomMediaUiState).media.title, willBe(updatedTitle))

        val updatedRating = 12
        uiState.value?.let { it.editRating(updatedRating) }
        assertThat(uiState.value?.review?.rating, willBe(updatedRating))

        val updatedReview = "updated review"
        uiState.value?.let { it.editReview(updatedReview) }
        assertThat(uiState.value?.review?.review, willBe(updatedReview))

        val updatedWatchContext = "updated watch context"
        uiState.value?.let { it.editWatchContext(updatedWatchContext) }
        assertThat(uiState.value?.review?.watchContext, willBe(updatedWatchContext))

        val updatedReviewDate = ReviewDate(2020, 12, 12)
        uiState.value?.let { it.editReviewDate(updatedReviewDate) }
        assertThat(uiState.value?.review?.reviewDate, willBe(updatedReviewDate))

        val expectedReview = MediaReview(
            updatedRating,
            updatedReview,
            updatedReviewDate,
            updatedWatchContext
        )

        val expectedMedia = CustomMedia(title = updatedTitle)

        uiState.value?.let { it.onConfirmReview() }
        verify(mockMediaRepository).addNewCustomMediaWithReview(
            argForWhich { equals(expectedMedia) },
            argForWhich { equals(expectedReview) }
        )
    }
}