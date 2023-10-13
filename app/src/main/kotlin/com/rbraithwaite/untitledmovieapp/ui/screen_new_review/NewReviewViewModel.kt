package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
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
    val media: CustomMedia,
    val isTitleEditable: Boolean,
    val editTitle: (String) -> Unit
): MediaUiState

@HiltViewModel
class NewReviewViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewReviewUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<NewReviewUiState?> = _uiState

    fun init(searchResult: NewReviewSearchResult?) {
        searchResult?.let {
            when (it) {
                is NewReviewSearchResult.NewCustomMedia -> {
                    val newCustomMedia = CustomMedia(
                        title = it.title
                    )
                    _uiState.update { _ ->
                        initialUiState(newCustomMedia)
                    }
                }
            }
        }
    }

    private fun initialUiState(media: Media): NewReviewUiState {
        val emptyReview = MediaReview()

        return when (media) {
            is CustomMedia -> {
                NewReviewUiState(
                    CustomMediaUiState(
                        media,
                        // REFACTOR [23-10-11 12:28a.m.] -- hardcoded - call 0L NEW_ENTITY or something.
                        media.id == 0L,
                        ::editTitle
                    ),
                    emptyReview,
                    ::onConfirmReview,
                    ::editRating,
                    ::editReview,
                    ::editWatchContext,
                    ::editReviewDate
                )
            }
        }
    }

    private fun onConfirmReview() {
        viewModelScope.launch {
            val review = _uiState.value!!.review
            when (val mediaUiState = _uiState.value!!.mediaUiState) {
                is CustomMediaUiState -> {
                    val customMedia = mediaUiState.media
                    if (customMedia.id == 0L) {
                        mediaRepository.addNewCustomMediaWithReview(
                            customMedia,
                            review
                        )
                    }
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