package com.rbraithwaite.untitledmovieapp.ui.screens.new_review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import java.time.Month

@Composable
fun NewReviewScreen(
    viewModel: NewReviewViewModel = viewModel(),
    onNavBack: () -> Unit,
    onConfirmReview: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateSafe = uiState ?: return

    Scaffold(
        topBar = {
            NewReviewScreenTopAppBar(
                onNavBack = onNavBack,
                onConfirmReview = {
                    uiStateSafe.onConfirmReview()
                    onConfirmReview()
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NewReviewScreenContent(uiStateSafe)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewReviewScreenTopAppBar(
    onNavBack: () -> Unit,
    onConfirmReview: () -> Unit
) {
    TopAppBar(
        title = {
            Text("New Review")
        },
        navigationIcon = {
            IconButton(onClick = onNavBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = onConfirmReview) {
                Icon(Icons.Filled.Done, contentDescription = null)
            }
        }
    )
}

@Composable
private fun NewReviewScreenContent(
    uiState: NewReviewUiState,
    screenState: NewReviewScreenState = rememberNewReviewScreenState()
) {
    if (screenState.shouldShowRatingDialog) {
        RatingPickerDialog(
            onDismiss = {  screenState.dismissRatingDialog() },
            onConfirm = { newRating ->
                screenState.dismissRatingDialog()
                uiState.editRating(newRating)
            }
        )
    }

    if (screenState.shouldShowDateDialog) {
        ReviewDatePickerDialog(
            initialReviewDate = uiState.review.reviewDate,
            onConfirm = {
                screenState.dismissDateDialog()
                uiState.editReviewDate(it)
            },
            onDismiss = {
                screenState.dismissDateDialog()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        when (val mediaUiState = uiState.mediaUiState) {
            is CustomMediaUiState -> {
                Text("title")
                if (mediaUiState.isTitleEditable) {
                    TextField(
                        value = mediaUiState.media.data.title,
                        onValueChange = {
                            mediaUiState.editTitle(it)
                        }
                    )
                }
                // TODO [23-12-20 2:06a.m.] -- define (!isTitleEditable) UI.
            }
            is TmdbMovieUiState -> {
                val tmdbMovie = mediaUiState.tmdbMovie

                Text("title: ${tmdbMovie.data.title} (${tmdbMovie.data.releaseDate?.year})")
                Text("overview: ${tmdbMovie.data.overview}")
                Divider()
            }
        }

        Text("rating")
        Button(onClick = { screenState.showRatingDialog() }) {
            Text(formatRating(uiState.review.rating))
        }

        Text("date")
        Button(onClick = { screenState.showDateDialog() }) {
            Text(formatReviewDate(uiState.review.reviewDate))
        }

        Text("review")
        TextField(
            value = uiState.review.review ?: "",
            onValueChange = { uiState.editReview(it) },
            minLines = 2
        )

        Text("where did you watch this?")
        Button(onClick = { /*TODO*/ }) {
            Text("add context")
        }
    }
}

// REFACTOR [23-10-11 1:52a.m.] -- move formatting logic to separate class - NewReviewFormatting.
private const val RATING_SUFFIX = "/ 10"
private const val NO_RATING = "-- $RATING_SUFFIX"
private const val NO_DATE = "None"

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

private fun formatReviewDate(reviewDate: ReviewDate?): String {
    if (reviewDate == null) {
        return NO_DATE
    }

    val yearText = reviewDate.year.toString()
    val monthText = reviewDate.month?.let { Month.of(it + 1).toString() + " " } ?: ""
    val dayText = reviewDate.day?.let { "$it " } ?: ""

    return "$dayText$monthText$yearText"
}


@Preview
@Composable
fun PreviewNewReviewScreen() {
    NewReviewScreen(
        onNavBack = {},
        onConfirmReview = {}
    )
}