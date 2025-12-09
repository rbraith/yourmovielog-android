package com.rbraithwaite.yourmovielog.ui.screens.new_review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberNewReviewScreenState(): NewReviewScreenState {
    return remember {
        NewReviewScreenState()
    }
}

class NewReviewScreenState {
    var shouldShowRatingDialog by mutableStateOf(false)
        private set
    var shouldShowDateDialog by mutableStateOf(false)
        private set

    fun showRatingDialog() {
        shouldShowRatingDialog = true
    }

    fun showDateDialog() {
        shouldShowDateDialog = true
    }

    fun dismissRatingDialog() {
        shouldShowRatingDialog = false
    }

    fun dismissDateDialog() {
        shouldShowDateDialog = false
    }
}