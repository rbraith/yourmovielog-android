package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import java.time.Month

@Composable
fun rememberNewReviewScreenState(
    initialMediaTitle: String?,
    initialReviewDate: ReviewDate?
): NewReviewScreenState {
    return remember(initialMediaTitle, initialReviewDate) {
        NewReviewScreenState(initialMediaTitle, initialReviewDate)
    }
}


class NewReviewScreenState(
    initialMediaTitle: String?,
    initialReviewDate: ReviewDate?
) {
    companion object {
        private const val RATING_SUFFIX = "/ 10"
        private const val NO_RATING = "-- $RATING_SUFFIX"

        private const val NO_DATE = "None"
    }

    var mediaTitle by mutableStateOf(initialMediaTitle ?: "")
    var userReview by mutableStateOf("")
    var shouldShowRatingDialog by mutableStateOf(false)
        private set
    var shouldShowDateDialog by mutableStateOf(false)
        private set

    private var rating: Int? = null
    var ratingString by mutableStateOf(formatRating(null))
        private set

    var reviewDate by mutableStateOf(initialReviewDate)
        private set
    var reviewDateString by mutableStateOf(formatDate(initialReviewDate))
        private set

    fun setNewReviewDate(reviewDate: ReviewDate?) {
        this.reviewDate = reviewDate
        reviewDateString = formatDate(reviewDate)
    }

    fun setRating(rating: Int?) {
        this.rating = rating
        ratingString = formatRating(rating)
    }

    private fun formatDate(date: ReviewDate?): String {
        if (date == null) {
            return NO_DATE
        }

        val yearText = date.year.toString()
        val monthText = date.month?.let { Month.of(it + 1).toString() + " " } ?: ""
        val dayText = date.day?.let { "$it " } ?: ""

        return "$dayText$monthText$yearText"
    }

    private fun formatRating(rating: Int?): String {
        return if (rating == null) {
            NO_RATING
        } else {
            val beforeDecimal = rating / 10
            val afterDecimal = rating % 10

            if (afterDecimal == 0) {
                beforeDecimal.toString()
            } else {
                "$beforeDecimal.$afterDecimal / $RATING_SUFFIX"
            }
        }
    }

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

    fun toReview(): MediaReview {
        return MediaReview(
            rating,
            userReview,
            reviewDate,
            null
        )
    }
}