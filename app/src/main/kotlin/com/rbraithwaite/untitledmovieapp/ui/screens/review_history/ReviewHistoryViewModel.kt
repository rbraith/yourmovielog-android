package com.rbraithwaite.untitledmovieapp.ui.screens.review_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ReviewHistoryUiState {
    data object Loading: ReviewHistoryUiState
    data class Success(val reviews: List<Review>): ReviewHistoryUiState
}

@HiltViewModel
class ReviewHistoryViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<ReviewHistoryUiState>(ReviewHistoryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val reviews = reviewRepository.getAllReviews(
                extras = setOf(Review.Extras.RelatedMedia::class)
            )

            _uiState.update {
                ReviewHistoryUiState.Success(reviews)
            }
        }
    }
}