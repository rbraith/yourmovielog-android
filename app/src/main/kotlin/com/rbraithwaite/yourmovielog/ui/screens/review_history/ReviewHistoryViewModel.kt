package com.rbraithwaite.yourmovielog.ui.screens.review_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewHistoryViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ReviewHistoryUiState>(ReviewHistoryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val reviews = reviewRepository.getAllReviews()
            _uiState.value = ReviewHistoryUiState.Success(reviews)
        }
    }
}
