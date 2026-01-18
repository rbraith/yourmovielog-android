package com.rbraithwaite.yourmovielog.ui.screens.new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.ReviewDate
import com.rbraithwaite.yourmovielog.core.data.TmdbData
import com.rbraithwaite.yourmovielog.core.data.TvShow
import com.rbraithwaite.yourmovielog.core.repositories.MediaRepository
import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
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
    ) : NewReviewUiState
}

sealed interface NewReviewMedia
data class NewReviewMovie(val movie: Movie) : NewReviewMedia
data class NewReviewTvShow(val tvShow: TvShow) : NewReviewMedia
data class NewReviewTvShowSeason(val newReviewTvShow: NewReviewTvShow, val tvShowSeason: TvShow.Season) : NewReviewMedia
data class NewReviewTvShowEpisode(
    val newReviewTvShowSeason: NewReviewTvShowSeason,
    val tvShowEpisode: TvShow.Episode
) : NewReviewMedia

@HiltViewModel
class NewReviewViewModel @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<NewReviewUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<NewReviewUiState?> = _uiState

    // Since the type of media being reviewed is undecided until the user actually confirms the review,
    // we maintain each type of media simultaneously. This retains the user's choices as they toggle
    // between these different media types.
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
        newReviewTvShowEpisode = NewReviewTvShowEpisode(newReviewTvShowSeason, TvShow.Episode("", 1, UUID.randomUUID(), now, now, null))
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

    private fun updateMediaTitles(newTitle: String) {
        newReviewMovie = newReviewMovie.copy(movie = newReviewMovie.movie.copy(title = newTitle))

        newReviewTvShow = newReviewTvShow.copy(tvShow = newReviewTvShow.tvShow.copy(title = newTitle))

        newReviewTvShowSeason = newReviewTvShowSeason.copy(newReviewTvShow = newReviewTvShow)

        // TODO [26-01-17 8:11p.m.] this was a bug - the episode title becomes stuck as the same as
        //  the show title. I'll need a different solution for updating episode titles
//        newReviewTvShowEpisode = newReviewTvShowEpisode.copy(
//            newReviewTvShowSeason = newReviewTvShowSeason,
//            tvShowEpisode = newReviewTvShowEpisode.tvShowEpisode.copy(title = newTitle)
//        )
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
        _uiState.update { state ->
            (state as? NewReviewUiState.EditReview)?.let {
                it.copy(review = it.review.block())
            } ?: state
        }
    }
}
