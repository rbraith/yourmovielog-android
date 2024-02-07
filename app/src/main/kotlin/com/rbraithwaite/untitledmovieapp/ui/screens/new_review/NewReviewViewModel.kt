package com.rbraithwaite.untitledmovieapp.ui.screens.new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewReviewUiState(
    val mediaUiState: MediaUiState,
    val review: Review,
    val onConfirmReview: () -> Unit,
    val editRating: (Int?) -> Unit,
    val editReview: (String?) -> Unit,
    val editWatchContext: (String?) -> Unit,
    val editReviewDate: (ReviewDate?) -> Unit
)

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
    private val customMediaRepository: CustomMediaRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<NewReviewUiState?> = MutableStateFlow(null)
    val uiState: StateFlow<NewReviewUiState?> = _uiState

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

    // TODO [24-02-2 12:17a.m.] broken.
//    private fun initialUiState(mediaUiState: MediaUiState): NewReviewUiState {
//        val emptyReview = Review()
//
//        return NewReviewUiState(
//            mediaUiState,
//            emptyReview,
//            ::onConfirmReview,
//            ::editRating,
//            ::editReview,
//            ::editWatchContext,
//            ::editReviewDate
//        )
//    }

    private fun onConfirmReview() {
        viewModelScope.launch {
            val review = _uiState.value!!.review
            when (val mediaUiState = _uiState.value!!.mediaUiState) {
                is CustomMediaUiState -> {
                    // TODO [24-02-2 12:17a.m.] broken.
//                    val customMedia = mediaUiState.media
//
//                    // REFACTOR [23-12-20 1:54a.m.] -- wtf am I doing here? am I using CustomMedia
//                    //  or SearchResult.CustomMedia?
//                    val customMovie2 = CustomMovie(
//                        customMedia.data.id,
//                        customMedia.data.title
//                    )
//
//                    if (customMedia.data.id == 0L) {
//                        customMediaRepository.addNewCustomMediaWithReview(
//                            customMovie2,
//                            review
//                        )
//                    }
                }
                // TODO [24-02-2 12:17a.m.] broken.
//                is TmdbMovieUiState -> {
//                    // TEST NEEDED [24-01-22 12:02a.m.] i need to fix NewReviewViewModelTests
//                    //  convert to delegate fakes to test this upsert call.
//                    customMediaRepository.upsertTmdbLite(mediaUiState.tmdbMovie.data)
//                    customMediaRepository.addTmdbMovieReview(
//                        tmdbMovieId = mediaUiState.tmdbMovie.data.id,
//                        review = review
//                    )
//                }
            }
        }
    }

    private fun editTitle(title: String) {
        // TODO [24-02-2 12:17a.m.] broken.
//        _uiState.update { state ->
//            state?.let {
//                if (it.mediaUiState is CustomMediaUiState) {
//                    val dataCopy = it.mediaUiState.media.data.run {
//                        copy(title = title)
//                    }
//                    val mediaCopy = it.mediaUiState.media.copy(data = dataCopy)
//                    val mediaUiStateCopy = it.mediaUiState.copy(media = mediaCopy)
//                    it.copy(mediaUiState = mediaUiStateCopy)
//                } else {
//                    it
//                }
//            }
//        }
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

    private fun updateReview(block: Review.() -> Review) {
        _uiState.update {state ->
            state?.let {
                it.copy(review = it.review.block())
            }
        }
    }
}