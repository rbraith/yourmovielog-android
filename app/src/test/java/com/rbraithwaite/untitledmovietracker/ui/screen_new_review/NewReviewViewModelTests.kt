package com.rbraithwaite.untitledmovietracker.ui.screen_new_review

import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class NewReviewViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockCustomMediaRepository: CustomMediaRepository = mock(CustomMediaRepository::class.java)

    private val viewModel = NewReviewViewModel(mockCustomMediaRepository)

    @Test
    fun beforeInit_uiStateIsNull() = runTest {
        val uiState = viewModel.uiState.value

        assertThat(uiState, willBe(null))
    }

    @Test
    fun afterInit_usingNewCustomMediaSearchResult() {
        // TODO [24-02-2 12:08a.m.] broken.
//        val expectedMediaTitle = "test"
//        // REFACTOR [23-12-20 2:45p.m.] -- hardcoded 0L id for new custom media.
//        val searchResult = SearchResult.CustomMedia(CustomMovie(0L, expectedMediaTitle))
//
//        val blankReview = Review()
//
//        viewModel.init(searchResult)
//
//        val uiState = viewModel.uiState.value
//
//        assertThat("", uiState?.mediaUiState is CustomMediaUiState)
//
//        val customMediaUiState = uiState?.mediaUiState as CustomMediaUiState
//        assertThat(customMediaUiState.media, willBeEqualTo(searchResult))
//        assertThat(customMediaUiState.isTitleEditable, willBe(true))
//        assertThat(uiState.review, willBeEqualTo(blankReview))
    }

    @Test
    fun afterInit_usingTmdbMovieResult() {
        // TODO [24-02-2 12:09a.m.] broken.
//        // GIVEN a TmdbMovie SearchResult
//        // -------------------------------------------
//
//        val searchResult = SearchResult.TmdbMovie(valueOf(tmdbLiteMovie()))
//
//        // WHEN the viewmodel is initialized with that result
//        // -------------------------------------------
//
//        viewModel.init(searchResult)
//
//        // THEN the MediaUiState is a TmdbMovieUiState
//        // -------------------------------------------
//
//        val mediaUiState = viewModel.uiState.value!!.mediaUiState
//
//        assertThat("", mediaUiState is TmdbMovieUiState)
//        assertThat((mediaUiState as TmdbMovieUiState).tmdbMovie, willBe(searchResult))
    }

    @Test
    fun uiStateIsNull_ifInitWithNull() {
        // TODO [24-02-2 12:39a.m.] broken.
//        viewModel.init(null)

//        assertThat(viewModel.uiState.value, willBe(null))
    }

    @Test
    fun onConfirmReview_forTmdbMovie() = runTest {
        // TODO [24-02-2 12:09a.m.] broken.
//        // GIVEN a new review for a tmdb movie
//        // -------------------------------------------
//
//        val expectedId = 123L
//
//        val searchResult = SearchResult.TmdbMovie(tmdbLiteMovie().withId(expectedId).build())
//
//        viewModel.init(searchResult)
//
//        // WHEN you update the review then confirm it
//        // -------------------------------------------
//
//        val uiState = viewModel.uiState
//
//        val updatedRating = 12
//        uiState.value?.let { it.editRating(updatedRating) }
//
//        val updatedReview = "updated review"
//        uiState.value?.let { it.editReview(updatedReview) }
//
//        val updatedWatchContext = "updated watch context"
//        uiState.value?.let { it.editWatchContext(updatedWatchContext) }
//
//        val updatedReviewDate = ReviewDate(2020, 12, 12)
//        uiState.value?.let { it.editReviewDate(updatedReviewDate) }
//
//        uiState.value?.let { it.onConfirmReview() }
//
//        // THEN the correct repository API is called with good arguments
//        // -------------------------------------------
//
//        val expectedReview = Review(
//            0,
//            updatedRating,
//            updatedReview,
//            updatedReviewDate,
//            updatedWatchContext
//        )
//
//        verify(mockCustomMediaRepository).addTmdbMovieReview(
//            expectedId,
//            expectedReview
//        )
    }

    @Test
    fun onConfirmReview_forNewCustomMedia() = runTest {
        // TODO [24-02-2 12:09a.m.] broken.
//        val initialTitle = "initial title"
//        // REFACTOR [23-12-20 2:46p.m.] -- hardcoded 0L id.
//        val searchResult = SearchResult.CustomMedia(CustomMovie(0L, initialTitle))
//
//        viewModel.init(searchResult)
//        val uiState = viewModel.uiState
//
//        val updatedTitle = "updated title"
//        (uiState.value?.mediaUiState as CustomMediaUiState).editTitle(updatedTitle)
//        assertThat((uiState.value?.mediaUiState as CustomMediaUiState).media.data.title, willBe(updatedTitle))
//
//        val updatedRating = 12
//        uiState.value?.let { it.editRating(updatedRating) }
//        assertThat(uiState.value?.review?.rating, willBe(updatedRating))
//
//        val updatedReview = "updated review"
//        uiState.value?.let { it.editReview(updatedReview) }
//        assertThat(uiState.value?.review?.review, willBe(updatedReview))
//
//        val updatedWatchContext = "updated watch context"
//        uiState.value?.let { it.editWatchContext(updatedWatchContext) }
//        assertThat(uiState.value?.review?.watchContext, willBe(updatedWatchContext))
//
//        val updatedReviewDate = ReviewDate(2020, 12, 12)
//        uiState.value?.let { it.editReviewDate(updatedReviewDate) }
//        assertThat(uiState.value?.review?.reviewDate, willBe(updatedReviewDate))
//
//        val expectedReview = Review(
//            0,
//            updatedRating,
//            updatedReview,
//            updatedReviewDate,
//            updatedWatchContext
//        )
//
//        val expectedMedia = CustomMovie(title = updatedTitle)
//
//        uiState.value?.let { it.onConfirmReview() }
//        verify(mockCustomMediaRepository).addNewCustomMediaWithReview(
//            argForWhich { equals(expectedMedia) },
//            argForWhich { equals(expectedReview) }
//        )
    }
}