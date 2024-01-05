package com.rbraithwaite.untitledmovieapp.ui.screens.new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewReviewUiState(
    val mediaUiState: MediaUiState,
    val review: MediaReview,
    val onConfirmReview: () -> Unit,
    val editRating: (Int?) -> Unit,
    val editReview: (String?) -> Unit,
    val editWatchContext: (String?) -> Unit,
    val editReviewDate: (ReviewDate?) -> Unit
)

sealed interface MediaUiState
data class CustomMediaUiState(
    val media: SearchResult.CustomMedia,
    val isTitleEditable: Boolean,
    val editTitle: (String) -> Unit
): MediaUiState

data class TmdbMovieUiState(
    val tmdbMovie: SearchResult.TmdbMovie
): MediaUiState

@HiltViewModel
class NewReviewViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewReviewUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<NewReviewUiState?> = _uiState

    fun init(searchResult: SearchResult?) {
        searchResult?.let {
            val mediaUiState: MediaUiState = when (it) {
                is SearchResult.CustomMedia -> {
                    // REFACTOR [23-12-20 1:56a.m.] -- should I convert to CustomMedia here?
                    //  what am I doing with CustomMedia vs SearchResult.CustomMedia? should the
                    //  search result just have a CustomMedia in it instead of duplicating the fields?
                    CustomMediaUiState(
                        media = it,
                        // REFACTOR [23-10-11 12:28a.m.] -- hardcoded - call 0L NEW_ENTITY or something.
                        isTitleEditable = it.id == 0L,
                        editTitle = ::editTitle
                    )
                }
                is SearchResult.TmdbMovie -> {
                    TmdbMovieUiState(it)
                }
                else -> {
                    // TO IMPLEMENT
                    TODO("Other search result types not yet implemented")
                }
            }

            _uiState.update { _ ->
                initialUiState(mediaUiState)
            }
        }
    }

    private fun initialUiState(mediaUiState: MediaUiState): NewReviewUiState {
        val emptyReview = MediaReview()

        return NewReviewUiState(
            mediaUiState,
            emptyReview,
            ::onConfirmReview,
            ::editRating,
            ::editReview,
            ::editWatchContext,
            ::editReviewDate
        )
    }

    private fun onConfirmReview() {
        viewModelScope.launch {
            val review = _uiState.value!!.review
            when (val mediaUiState = _uiState.value!!.mediaUiState) {
                is CustomMediaUiState -> {
                    val customMedia = mediaUiState.media

                    // REFACTOR [23-12-20 1:54a.m.] -- wtf am I doing here? am I using CustomMedia
                    //  or SearchResult.CustomMedia?
                    val customMedia2 = CustomMedia(
                        customMedia.id,
                        customMedia.title
                    )

                    if (customMedia.id == 0L) {
                        mediaRepository.addNewCustomMediaWithReview(
                            customMedia2,
                            review
                        )
                    }
                }
                is TmdbMovieUiState -> {
                    mediaRepository.addTmdbMovieReview(
                        tmdbMovieId = mediaUiState.tmdbMovie.id,
                        review = review
                    )
                }
            }
        }
    }

    private fun editTitle(title: String) {
        _uiState.update { state ->
            state?.let {
                if (it.mediaUiState is CustomMediaUiState) {
                    val mediaCopy = it.mediaUiState.media.copy(title = title)
                    val mediaUiStateCopy = it.mediaUiState.copy(media = mediaCopy)
                    it.copy(mediaUiState = mediaUiStateCopy)
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
            state?.let {
                it.copy(review = it.review.block())
            }
        }
    }
}