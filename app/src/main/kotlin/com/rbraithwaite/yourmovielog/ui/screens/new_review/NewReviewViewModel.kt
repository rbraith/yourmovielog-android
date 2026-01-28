package com.rbraithwaite.yourmovielog.ui.screens.new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.yourmovielog.core.data.Media
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
import kotlin.reflect.KClass

sealed interface NewReviewUiState {
    data object Loading : NewReviewUiState
    data class EditReview(
        val media: Media,
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
        val onSelectMediaType: (KClass<out Media>) -> Unit,
        val onConfirmReview: () -> Unit,
    ) : NewReviewUiState
}

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
    private var movie: Movie
    private var tvShow: TvShow
    private var tvSeason: TvShow.Season
    private var tvEpisode: TvShow.Episode

    private var tmdbData: TmdbData? = null

    private val defaultMedia: Media
        get() = movie

    init {
        val now = LocalDateTime.now()
        movie = Movie("", UUID.randomUUID(), now, now, null)
        tvShow = TvShow("", UUID.randomUUID(), now, now, null)
        tvSeason = TvShow.Season(1, UUID.randomUUID(), now, now, null)
        tvEpisode = TvShow.Episode("", 1, UUID.randomUUID(), now, now, null)
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
        movie = movie.copy(title = newTitle)
        tvShow = tvShow.copy(title = newTitle)
    }

    private fun initialUiState(
        media: Media,
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
            ::onSelectMediaType,
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

    private fun onSelectMediaType(mediaType: KClass<out Media>) {
        _uiState.update { state ->
            (state as? NewReviewUiState.EditReview)?.let {
                when (mediaType) {
                    Movie::class -> it.copy(media = movie)
                    TvShow::class -> it.copy(media = tvShow)
                    TvShow.Season::class -> it.copy(media = tvSeason)
                    TvShow.Episode::class -> it.copy(media = tvEpisode)
                    else -> it
                }
            } ?: state
        }
    }

    private fun onConfirmReview() {
        viewModelScope.launch {
            val editReviewState = _uiState.value as? NewReviewUiState.EditReview ?: return@launch

            // TODO [26-01-27 8:43p.m.] all this should be in a transaction.

            mediaRepository.addMedia(editReviewState.media)
            reviewRepository.addReview(editReviewState.review, editReviewState.media.uuid)

            // When the media being reviewed is a tv season or episode, we should also persist the
            // new parent media types (show for a season, and show + season for an episode)
            val media = editReviewState.media
            if (media is TvShow.Season) {
                mediaRepository.addMedia(tvShow)
            } else if (media is TvShow.Episode) {
                mediaRepository.addMedia(tvShow)
                mediaRepository.addMedia(tvSeason)
            }
        }
    }

    private fun editTitle(title: String) {
        _uiState.update { state ->
            updateMediaTitles(title)
            state?.let {
                if (it is NewReviewUiState.EditReview) {
                    when (it.media) {
                        is Movie -> it.copy(media = movie)
                        is TvShow -> it.copy(media = tvShow)
                        is TvShow.Season -> it.copy(media = tvSeason)
                        is TvShow.Episode -> it.copy(media = tvEpisode)
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
