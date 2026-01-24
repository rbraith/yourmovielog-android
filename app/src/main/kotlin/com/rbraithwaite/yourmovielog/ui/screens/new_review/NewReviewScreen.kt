package com.rbraithwaite.yourmovielog.ui.screens.new_review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.ReviewDate
import com.rbraithwaite.yourmovielog.core.data.TvShow
import com.rbraithwaite.yourmovielog.ui.debug.DebugPlaceholder
import com.rbraithwaite.yourmovielog.ui.screens.new_review.components.MediaTypeSelector
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
                onNavBack = {
                    // TO IMPLEMENT
                },
                onConfirmReview = {
                    // TODO [25-12-1 5:10p.m.] broken.
//                    uiStateSafe.onConfirmReview()
//                    onConfirmReview()
                }
            )
        }
    ) { contentPadding ->
        val screenState = rememberNewReviewScreenState()

        Box(modifier = Modifier.padding(contentPadding)) {
            NewReviewScreenContent(uiStateSafe, screenState)
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
    screenState: NewReviewScreenState
) {
    if (screenState.shouldShowRatingDialog) {
        RatingPickerDialog(
            initialRating = screenState.dialogInitialRating,
            onDismiss = {
                screenState.dismissRatingDialog()
            },
            onConfirm = { newRating ->
                screenState.dismissRatingDialog()
                (uiState as? NewReviewUiState.EditReview)?.editRating?.invoke(newRating)
            }
        )
    }

    if (screenState.shouldShowDateDialog) {
        ReviewDatePickerDialog(
            initialReviewDate = screenState.dialogInitialReviewDate,
            onConfirm = { newReviewDate ->
                screenState.dismissDateDialog()
                (uiState as? NewReviewUiState.EditReview)?.editReviewDate?.invoke(newReviewDate)
            },
            onDismiss = {
                screenState.dismissDateDialog()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        when (uiState) {
            is NewReviewUiState.Loading -> {
                // TO IMPLEMENT
                DebugPlaceholder(label = "loading")
            }
            is NewReviewUiState.EditReview -> {
                EditReviewView(uiState, screenState)
            }
        }
    }
}

@Composable
private fun EditReviewView(
    uiState: NewReviewUiState.EditReview,
    screenState: NewReviewScreenState
) {
    MediaTypeSelector(
        selectedMediaType = uiState.media::class,
        onSelectMediaType = { selectedMediaType ->
            uiState.onSelectMediaType(selectedMediaType)
        }
    )

    when (uiState.media) {
        is Movie -> {
            Text("movie title")
            TextField(
                value = uiState.media.title,
                onValueChange = { uiState.editTitle(it) }
            )
        }
        is TvShow -> {
            DebugPlaceholder(label = "tv show fields")
        }
        is TvShow.Season -> {
            DebugPlaceholder(label = "tv season fields")
        }
        is TvShow.Episode -> {
            DebugPlaceholder(label = "tv episode fields")
        }
    }

    ReviewSection(
        uiState.review,
        onSelectNewRating = { currentRating ->
            screenState.showRatingDialog(initialRating = currentRating)
        },
        onChangeReview = {
            uiState.editReview(it)
        },
        onSelectNewReviewDate = { currentReviewDate ->
            screenState.showDateDialog(initialReviewDate = currentReviewDate)
        },
        onChangeWatchContext = {
            uiState.editWatchContext(it)
        }
    )
}

@Composable
private fun ReviewSection(
    currentReview: MediaReview,
    onSelectNewRating: (currentRating: Int?) -> Unit,
    onChangeReview: (currentReview: String?) -> Unit,
    onSelectNewReviewDate: (currentReviewDate: ReviewDate?) -> Unit,
    onChangeWatchContext: (currentWatchContext: String?) -> Unit
) {
    Column {
        Text("rating")
        Button(onClick = { onSelectNewRating(currentReview.rating) }) {
            Text(formatRating(currentReview.rating))
        }

        Text("review")
        TextField(
            value = currentReview.review ?: "",
            onValueChange = { onChangeReview(it) }
        )

        Text("review date")
        Button(onClick = { onSelectNewReviewDate(currentReview.reviewDate) }) {
            Text(formatReviewDate(currentReview.reviewDate))
        }

        Text("watch context")
        TextField(
            value = currentReview.watchContext ?: "",
            onValueChange = { onChangeWatchContext(it) }
        )
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
