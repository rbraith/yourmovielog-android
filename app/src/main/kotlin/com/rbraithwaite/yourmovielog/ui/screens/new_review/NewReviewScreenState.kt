package com.rbraithwaite.yourmovielog.ui.screens.new_review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.rbraithwaite.yourmovielog.core.data.ReviewDate

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

    var dialogInitialRating: Int? = null
        private set

    var dialogInitialReviewDate: ReviewDate? = null
        private set

    fun showRatingDialog(initialRating: Int?) {
        dialogInitialRating = initialRating
        shouldShowRatingDialog = true
    }

    fun showDateDialog(initialReviewDate: ReviewDate?) {
        dialogInitialReviewDate = initialReviewDate
        shouldShowDateDialog = true
    }

    fun dismissRatingDialog() {
        shouldShowRatingDialog = false
    }

    fun dismissDateDialog() {
        shouldShowDateDialog = false
    }
}
