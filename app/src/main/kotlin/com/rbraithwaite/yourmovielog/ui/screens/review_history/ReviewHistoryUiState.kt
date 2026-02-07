package com.rbraithwaite.yourmovielog.ui.screens.review_history

import com.rbraithwaite.yourmovielog.core.data.MediaReview

sealed interface ReviewHistoryUiState {
    data object Loading : ReviewHistoryUiState

    data class Success(val reviews: List<MediaReview>) : ReviewHistoryUiState
}
