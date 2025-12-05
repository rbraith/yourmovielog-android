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
}