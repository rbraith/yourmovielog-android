package com.rbraithwaite.untitledmovieapp.ui.screens.review_history

import androidx.lifecycle.ViewModel
import com.rbraithwaite.untitledmovieapp.TempWipData
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface ReviewHistoryUiState {
    data object Loading: ReviewHistoryUiState
    data class Success(val reviews: List<MediaReview>): ReviewHistoryUiState
}

@HiltViewModel
class ReviewHistoryViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow<ReviewHistoryUiState>(
//        ReviewHistoryUiState.Loading
        ReviewHistoryUiState.Success(TempWipData.allReviews)
    )
    val uiState = _uiState.asStateFlow()
}