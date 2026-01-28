package com.rbraithwaite.yourmovielog.ui.screen_new_review

import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.TvShow
import com.rbraithwaite.yourmovielog.test_utils.fakes.repositories.FakeMediaRepository
import com.rbraithwaite.yourmovielog.test_utils.fakes.repositories.FakeReviewRepository
import com.rbraithwaite.yourmovielog.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.yourmovielog.test_utils.willBe
import com.rbraithwaite.yourmovielog.ui.screens.new_review.NewReviewArgs
import com.rbraithwaite.yourmovielog.ui.screens.new_review.NewReviewUiState
import com.rbraithwaite.yourmovielog.ui.screens.new_review.NewReviewViewModel
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import kotlin.reflect.KClass

class NewReviewViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeMediaRepository = FakeMediaRepository()
    private val fakeReviewRepository = FakeReviewRepository()

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

        assertThat("", editReview.media is Movie)
        val movie = editReview.media as Movie

        assertThat(movie.title, willBe(expectedMediaTitle))
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
        val movie = editReviewState.media as Movie
        assertThat(movie.title, willBe(expectedTitle))
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
        val mediaList = fakeMediaRepository.getMedia()
        assertThat(mediaList.size, willBe(1))

        val movie = mediaList[0] as Movie
        assertThat(movie.title, willBe(movieTitle))

        val reviewList = fakeReviewRepository.getReviews()
        assertThat(reviewList.size, willBe(1))

        assertThat(reviewList[0].rating, willBe(reviewRating))
    }

    @Test
    fun onConfirmMedia_ParentTvMediaArePersistedForEpisodes() = runTest {
        // GIVEN there is an active review for a tv episode of a "new media" show
        // ------------------------------------------
        val tvShowTitle = "tv show title"
        viewModel.init(NewReviewArgs.NewMedia(tvShowTitle))

        val editReviewState = viewModel.uiState.value as NewReviewUiState.EditReview
        editReviewState.onSelectMediaType(TvShow.Episode::class)

        // WHEN the review is confirmed
        // ------------------------------------------
        editReviewState.onConfirmReview()

        // THEN the new tv show and seasons are also persisted along with the new episode
        // ------------------------------------------
        val mediaList = fakeMediaRepository.getMedia()
        assertThat(mediaList.size, willBe(3))
        assertThat("", mediaList.any { it is TvShow && it.title == tvShowTitle })
        assertThat("", mediaList.any { it is TvShow.Season && it.seasonNumber == 1 })
        assertThat("", mediaList.any { it is TvShow.Episode && it.episodeNumber == 1 })
    }

    @Test
    fun selectTvShowMediaType() = runTest {
        paramTest_selectMediaType(TvShow::class)
    }

    @Test
    fun selectTvSeasonMediaType() = runTest {
        paramTest_selectMediaType(TvShow.Season::class)
    }

    @Test
    fun selectTvEpisodeMediaType() = runTest {
        paramTest_selectMediaType(TvShow.Episode::class)
    }

    private fun paramTest_selectMediaType(mediaType: KClass<out Media>) {
        // GIVEN a normal starting state
        // ------------------------------------------
        viewModel.init(NewReviewArgs.NewMedia("dummy"))

        var editReviewState = viewModel.uiState.value as NewReviewUiState.EditReview
        assertThat(editReviewState.media::class, willBe(Movie::class))

        // WHEN the user selects a new media type
        // ------------------------------------------
        editReviewState.onSelectMediaType(mediaType)

        // THEN the ui state's media type matches the selected type
        // ------------------------------------------
        editReviewState = viewModel.uiState.value as NewReviewUiState.EditReview
        assertThat(editReviewState.media::class, willBe(mediaType))
    }
}
