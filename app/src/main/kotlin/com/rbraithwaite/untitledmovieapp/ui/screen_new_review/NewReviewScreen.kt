package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor
import java.time.LocalDate
import java.time.Month

@Composable
fun NewReviewScreen(
    media: NewReviewSearchResult?
) {
    var titleString by remember(media) { mutableStateOf(when(media) {
        is NewReviewSearchResult.CustomMedia -> {
            media.title
        }
        null -> ""
    }) }

    var userReview by remember { mutableStateOf("") }

    var shouldShowRatingDialog by remember { mutableStateOf(false) }
    var shouldShowDateDialog by remember { mutableStateOf(false) }
    var ratingString by remember { mutableStateOf("--") }

    var reviewDate: ReviewDate? by remember { mutableStateOf(ReviewDate(LocalDate.now())) }

    if (shouldShowRatingDialog) {
        RatingPickerDialog(
            onDismiss = { shouldShowRatingDialog = false },
            onConfirm = { newRating ->
                shouldShowRatingDialog = false
                ratingString = newRating?.let { formatRatingForNewReview(it) } ?: "--"
            }
        )
    }

    if (shouldShowDateDialog) {
        ReviewDatePickerDialog(
            initialReviewDate = reviewDate,
            onConfirm = {
                shouldShowDateDialog = false
                reviewDate = it
            },
            onDismiss = {
                shouldShowDateDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        Text("title")
        TextField(value = titleString, onValueChange = {
            titleString = it
        })

        Text("rating")
        Button(onClick = { shouldShowRatingDialog = true }) {
            Text("$ratingString / 10")
        }

        Text("date")
        Button(onClick = { shouldShowDateDialog = true }) {
            Text(formatDate(reviewDate))
        }

        Text("review")
        TextField(
            value = userReview,
            onValueChange = { userReview = it },
            minLines = 2
        )

        Text("where did you watch this?")
        Button(onClick = { /*TODO*/ }) {
            Text("add context")
        }
    }
}

private fun formatDate(date: ReviewDate?): String {
    if (date == null) {
        return "None"
    }

    val yearText = date.year.toString()
    val monthText = date.month?.let { Month.of(it + 1).toString() + " " } ?: ""
    val dayText = date.day?.let { "$it " } ?: ""

    return "$dayText$monthText$yearText"
}

private fun formatRatingForNewReview(rating: Int?): String {
    return if (rating == null) {
        ""
    } else {
        val beforeDecimal = rating / 10
        val afterDecimal = rating % 10

        if (afterDecimal == 0) {
            beforeDecimal.toString()
        } else {
            "$beforeDecimal.$afterDecimal"
        }
    }
}

@Preview
@Composable
fun PreviewNewReviewScreen() {
    NewReviewScreen(null)
}