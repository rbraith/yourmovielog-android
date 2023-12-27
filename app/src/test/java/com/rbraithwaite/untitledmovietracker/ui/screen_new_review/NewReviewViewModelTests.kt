package com.rbraithwaite.untitledmovietracker.ui.screen_new_review

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.CustomMediaUiState
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.TmdbMovieUiState
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
        // REFACTOR [23-12-20 2:45p.m.] -- hardcoded 0L id for new custom media.
        val searchResult = SearchResult.CustomMedia(0L, expectedMediaTitle)

        val blankReview = MediaReview()

        viewModel.init(searchResult)

        val uiState = viewModel.uiState.value

        assertThat("", uiState?.mediaUiState is CustomMediaUiState)

        val customMediaUiState = uiState?.mediaUiState as CustomMediaUiState
        assertThat(customMediaUiState.media, willBeEqualTo(searchResult))
        assertThat(customMediaUiState.isTitleEditable, willBe(true))
        assertThat(uiState.review, willBeEqualTo(blankReview))
    }

    @Test
    fun afterInit_usingTmdbMovieResult() {
        // GIVEN a TmdbMovie SearchResult
        // -------------------------------------------

        // REFACTOR [23-12-20 2:52p.m.] -- use something else for this data, a builder or something.
        val searchResult = SearchResult.TmdbMovie(
            id = 123,
            title = "",
            overview = "",
            posterPath = null,
            genreIds = emptyList(),
            popularity = 1.23f,
            releaseDate = null,
            voteAverage = 3.21f,
            voteCount = 456
        )

        // WHEN the viewmodel is initialized with that result
        // -------------------------------------------

        viewModel.init(searchResult)

        // THEN the MediaUiState is a TmdbMovieUiState
        // -------------------------------------------

        val mediaUiState = viewModel.uiState.value!!.mediaUiState

        assertThat("", mediaUiState is TmdbMovieUiState)
        assertThat((mediaUiState as TmdbMovieUiState).tmdbMovie, willBe(searchResult))
    }

    @Test
    fun uiStateIsNull_ifInitWithNull() {
        viewModel.init(null)

        assertThat(viewModel.uiState.value, willBe(null))
    }

    @Test
    fun onConfirmReview_forTmdbMovie() = runTest {
        // GIVEN a new review for a tmdb movie
        // -------------------------------------------

        val expectedId = 123

        // REFACTOR [23-12-27 1:50p.m.] -- this should be a test data builder.
        val searchResult = SearchResult.TmdbMovie(
            id = expectedId,
            title = "",
            overview = "",
            posterPath = null,
            genreIds = emptyList(),
            popularity = 1.23f,
            releaseDate = null,
            voteAverage = 3.21f,
            voteCount = 456
        )

        viewModel.init(searchResult)

        // WHEN you update the review then confirm it
        // -------------------------------------------

        val uiState = viewModel.uiState

        val updatedRating = 12
        uiState.value?.let { it.editRating(updatedRating) }

        val updatedReview = "updated review"
        uiState.value?.let { it.editReview(updatedReview) }

        val updatedWatchContext = "updated watch context"
        uiState.value?.let { it.editWatchContext(updatedWatchContext) }

        val updatedReviewDate = ReviewDate(2020, 12, 12)
        uiState.value?.let { it.editReviewDate(updatedReviewDate) }

        uiState.value?.let { it.onConfirmReview() }

        // THEN the correct repository API is called with good arguments
        // -------------------------------------------

        val expectedReview = MediaReview(
            updatedRating,
            updatedReview,
            updatedReviewDate,
            updatedWatchContext
        )

        verify(mockMediaRepository).addTmdbMovieReview(
            expectedId,
            expectedReview
        )
    }

    @Test
    fun onConfirmReview_forNewCustomMedia() = runTest {
        val initialTitle = "initial title"
        // REFACTOR [23-12-20 2:46p.m.] -- hardcoded 0L id.
        val searchResult = SearchResult.CustomMedia(0L, initialTitle)

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