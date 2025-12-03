package com.rbraithwaite.untitledmovieapp.ui.screens.new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.Movie
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.core.data.TmdbData
import com.rbraithwaite.untitledmovieapp.core.data.TvShow
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

sealed interface NewReviewUiState {
    data object Loading : NewReviewUiState
    data class EditReview(
        val media: NewReviewMedia,
        val tmdbData: TmdbData?,
        val review: MediaReview,

        // edit media callbacks
        val editTitle: (String) -> Unit,

        // edit review callbacks
        val editRating: (Int?) -> Unit,
        val editReview: (String?) -> Unit,
        val editWatchContext: (String?) -> Unit,
        val editReviewDate: (ReviewDate?) -> Unit,

        // other callbacks
        val onConfirmReview: () -> Unit,
    ): NewReviewUiState
}

sealed interface NewReviewMedia
data class NewReviewMovie(val movie: Movie): NewReviewMedia
data class NewReviewTvShow(val tvShow: TvShow): NewReviewMedia
data class NewReviewTvShowSeason(val newReviewTvShow: NewReviewTvShow, val tvShowSeason: TvShow.Season): NewReviewMedia
data class NewReviewTvShowEpisode(val newReviewTvShowSeason: NewReviewTvShowSeason, val tvShowEpisode: TvShow.Episode): NewReviewMedia

sealed interface MediaUiState
data class CustomMediaUiState(
    // TODO [24-02-2 12:17a.m.] broken.
//    val media: SearchResult.CustomMedia,
    val isTitleEditable: Boolean,
    val editTitle: (String) -> Unit
): MediaUiState

// TODO [24-02-2 12:17a.m.] broken.
//data class TmdbMovieUiState(
////    val tmdbMovie: SearchResult.TmdbMovie
//): MediaUiState

@HiltViewModel
class NewReviewViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<NewReviewUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<NewReviewUiState?> = _uiState

    private var newReviewMovie: NewReviewMovie
    private var newReviewTvShow: NewReviewTvShow
    private var newReviewTvShowSeason: NewReviewTvShowSeason
    private var newReviewTvShowEpisode: NewReviewTvShowEpisode

    private var tmdbData: TmdbData? = null

    private val defaultMedia: NewReviewMedia
        get() = newReviewMovie

    init {
        val now = LocalDateTime.now()
        newReviewMovie = NewReviewMovie(Movie("", UUID.randomUUID(), now, now, null))
        newReviewTvShow = NewReviewTvShow(TvShow("", UUID.randomUUID(), now, now, null))
        newReviewTvShowSeason = NewReviewTvShowSeason(newReviewTvShow, TvShow.Season(1, UUID.randomUUID(), now, now, null))
        newReviewTvShowEpisode = NewReviewTvShowEpisode(newReviewTvShowSeason,TvShow.Episode("", 1, UUID.randomUUID(), now, now, null))
    }

    fun init(args: NewReviewArgs?) {
        when (args) {
            is NewReviewArgs.NewMedia -> {
                updateMediaTitles(args.title)
                _uiState.value = initialUiState(defaultMedia, null)
            }
            else -> {
                // TO IMPLEMENT
            }
        }
    }

    // TODO [24-02-2 12:17a.m.] broken.
//    fun init(searchResult: SearchResult?) {
//        searchResult?.let {
//            val mediaUiState: MediaUiState = when (it) {
//                is SearchResult.CustomMedia -> {
//                    // REFACTOR [23-12-20 1:56a.m.] -- should I convert to CustomMedia here?
//                    //  what am I doing with CustomMedia vs SearchResult.CustomMedia? should the
//                    //  search result just have a CustomMedia in it instead of duplicating the fields?
//                    CustomMediaUiState(
//                        media = it,
//                        // REFACTOR [23-10-11 12:28a.m.] -- hardcoded - call 0L NEW_ENTITY or something.
//                        isTitleEditable = it.data.id == 0L,
//                        editTitle = ::editTitle
//                    )
//                }
//                is SearchResult.TmdbMovie -> {
//                    TmdbMovieUiState(it)
//                }
//                else -> {
//                    // TO IMPLEMENT
//                    TODO("Other search result types not yet implemented")
//                }
//            }
//
//            _uiState.update { _ ->
//                initialUiState(mediaUiState)
//            }
//        }
//    }

    private fun updateMediaTitles(newTitle: String) {
        newReviewMovie = newReviewMovie.copy(movie = newReviewMovie.movie.copy(title = newTitle))

        newReviewTvShow = newReviewTvShow.copy(tvShow = newReviewTvShow.tvShow.copy(title = newTitle))

        newReviewTvShowSeason = newReviewTvShowSeason.copy(newReviewTvShow = newReviewTvShow)

        newReviewTvShowEpisode = newReviewTvShowEpisode.copy(
            newReviewTvShowSeason = newReviewTvShowSeason,
            tvShowEpisode = newReviewTvShowEpisode.tvShowEpisode.copy(title = newTitle)
        )
    }

    private fun initialUiState(
        media: NewReviewMedia,
        tmdbData: TmdbData?
    ): NewReviewUiState {
        return NewReviewUiState.EditReview(
            media,
            tmdbData,
            createEmptyReview(),
            ::editTitle,
            ::editRating,
            ::editReview,
            ::editWatchContext,
            ::editReviewDate,
            ::onConfirmReview
        )
    }

    private fun createEmptyReview(): MediaReview {
        return MediaReview(
            // TODO [25-11-27 3:59p.m.] do I want to be generating the UUID at this point?
            UUID.randomUUID(),
            null,
            null,
            null,
            null,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
    }


    private fun onConfirmReview() {
        viewModelScope.launch {
            // TODO [25-12-2 2:57a.m.] handle null review or media case.
            val review = (_uiState.value as? NewReviewUiState.EditReview)?.review ?: return@launch
            val media = (_uiState.value as? NewReviewUiState.EditReview)?.media ?: return@launch

            // TODO [25-12-2 2:59a.m.] there's more subtlety that will be needed here, or somewhere in this viewmodel.
            //  new media have editable values, while existing media or tmdb-derived media don't.

            when (media) {
                is NewReviewMovie -> {
                    mediaRepository.addMedia(media.movie)
                    reviewRepository.addReview(review, media.movie.uuid)
                }
                else -> {
                    // TODO [25-12-2 3:01a.m.] will need to consider how to handle tv seasons/episodes,
                    //  since they also require the tv show data
                    //  actually probably just: upsert (maybe?) tv show; then add season or episode w/ tv show uuid.
                    TODO("Other media types not yet implemented")
                }
            }
        }

//        viewModelScope.launch {
//            val review = _uiState.value!!.review
//            when (val mediaUiState = _uiState.value!!.mediaUiState) {
//                is CustomMediaUiState -> {
//                    // TODO [24-02-2 12:17a.m.] broken.
////                    val customMedia = mediaUiState.media
////
////                    // REFACTOR [23-12-20 1:54a.m.] -- wtf am I doing here? am I using CustomMedia
////                    //  or SearchResult.CustomMedia?
////                    val customMovie2 = CustomMovie(
////                        customMedia.data.id,
////                        customMedia.data.title
////                    )
////
////                    if (customMedia.data.id == 0L) {
////                        customMediaRepository.addNewCustomMediaWithReview(
////                            customMovie2,
////                            review
////                        )
////                    }
//                }
//                // TODO [24-02-2 12:17a.m.] broken.
////                is TmdbMovieUiState -> {
////                    // TEST NEEDED [24-01-22 12:02a.m.] i need to fix NewReviewViewModelTests
////                    //  convert to delegate fakes to test this upsert call.
////                    customMediaRepository.upsertTmdbLite(mediaUiState.tmdbMovie.data)
////                    customMediaRepository.addTmdbMovieReview(
////                        tmdbMovieId = mediaUiState.tmdbMovie.data.id,
////                        review = review
////                    )
////                }
//            }
//        }
    }

    private fun editTitle(title: String) {
        _uiState.update { state ->
            updateMediaTitles(title)
            state?.let {
                if (it is NewReviewUiState.EditReview) {
                    when (it.media) {
                        is NewReviewMovie -> it.copy(media = newReviewMovie)
                        is NewReviewTvShow -> it.copy(media = newReviewTvShow)
                        is NewReviewTvShowSeason -> it.copy(media = newReviewTvShowSeason)
                        is NewReviewTvShowEpisode -> it.copy(media = newReviewTvShowEpisode)
                    }
                } else {
                    it
                }
            }
        }
    }

    private fun editRating(rating: Int?) {
        updateReview {
            copy(rating = rating)
        }
    }

    private fun editReview(review: String?) {
        updateReview {
            copy(review = review)
        }
    }

    private fun editReviewDate(reviewDate: ReviewDate?) {
        updateReview {
            copy(reviewDate = reviewDate)
        }
    }

    private fun editWatchContext(watchContext: String?) {
        updateReview {
            copy(watchContext = watchContext)
        }
    }

    private fun updateReview(block: MediaReview.() -> MediaReview) {
        _uiState.update {state ->
            (state as? NewReviewUiState.EditReview)?.let {
                it.copy(review = it.review.block())
            } ?: state
        }
    }
}