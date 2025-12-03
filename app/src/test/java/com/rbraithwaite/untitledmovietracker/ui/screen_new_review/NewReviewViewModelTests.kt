package com.rbraithwaite.untitledmovietracker.ui.screen_new_review

import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewArgs
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewMovie
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewUiState
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.argForWhich
import org.mockito.kotlin.verify

class NewReviewViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDependencyManager = TestDependencyManager(
        mainDispatcherRule.testScope,
        mainDispatcherRule.testDispatcher
    )
    private val fakeMediaDao = testDependencyManager.mediaDao
    private val fakeReviewDao = testDependencyManager.reviewDao
    private val fakeMediaRepository = testDependencyManager.mediaRepository
    private val fakeReviewRepository = testDependencyManager.reviewRepository

    private val viewModel = NewReviewViewModel(fakeMediaRepository, fakeReviewRepository)

    @Test
    fun beforeInit_uiStateIsNull() = runTest {
        val uiState = viewModel.uiState.value

        assertThat(uiState, willBe(null))
    }

    @Test
    fun afterInit_usingNewMediaArg() {
        // GIVEN the viewmodel is initialized with a 'new media' argument
        // ------------------------------------------
        val expectedMediaTitle = "test"
        viewModel.init(NewReviewArgs.NewMedia(expectedMediaTitle))


        // WHEN the initial ui state is queried
        // ------------------------------------------
        val uiState = viewModel.uiState.value


        // THEN the ui state should be a movie with the new media title
        // ------------------------------------------
        assertThat("", uiState is NewReviewUiState.EditReview)
        val editReview = uiState as NewReviewUiState.EditReview

        assertThat("", editReview.media is NewReviewMovie)
        val newReviewMovie = editReview.media as NewReviewMovie

        assertThat(newReviewMovie.movie.title, willBe(expectedMediaTitle))
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
        viewModel.init(null)

        assertThat(viewModel.uiState.value, willBe(null))
    }

    @Test
    fun editTitleTest() {
        // GIVEN a review of a 'new media' movie
        // ------------------------------------------
        val initialTitle = "initial title"

        viewModel.init(NewReviewArgs.NewMedia(initialTitle))


        // WHEN that movie's title is edited
        // ------------------------------------------
        val expectedTitle = "edited title"

        var editReviewState = viewModel.uiState.value as NewReviewUiState.EditReview
        editReviewState.editTitle(expectedTitle)


        // THEN the new title is updated in the ui state
        // ------------------------------------------
        editReviewState = viewModel.uiState.value as NewReviewUiState.EditReview
        val movie = editReviewState.media as NewReviewMovie
        assertThat(movie.movie.title, willBe(expectedTitle))
    }

    @Test
    fun onConfirmReview_forNewMedia() = runTest {
        // GIVEN there is an active new review for a new media
        // ------------------------------------------
        val movieTitle = "movie title"
        val reviewRating = 12

        viewModel.init(NewReviewArgs.NewMedia(movieTitle))

        val editReviewState = viewModel.uiState.value as NewReviewUiState.EditReview
        editReviewState.editTitle(movieTitle)
        editReviewState.editRating(reviewRating)


        // WHEN the review is confirmed
        // ------------------------------------------
        editReviewState.onConfirmReview()


        // THEN both the review and the media are correctly persisted
        // ------------------------------------------
        verify(testDependencyManager.mediaDao.mock).insertMovie(argForWhich {
            this.title == movieTitle
        })

        verify(testDependencyManager.reviewDao.mock).insertReview(argForWhich {
            this.rating == reviewRating
        })
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