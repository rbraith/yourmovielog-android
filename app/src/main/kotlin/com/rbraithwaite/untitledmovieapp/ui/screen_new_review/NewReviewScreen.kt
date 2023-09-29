package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.data.ReviewDate
import java.time.LocalDate
import java.time.Month
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewReviewScreen(
    viewModel: NewReviewScreenViewModel = viewModel(),
    onNavBack: () -> Unit,
    onConfirmReview: () -> Unit
) {
    val screenState = rememberNewReviewScreenState(
        initialMediaTitle = viewModel.initialMediaTitle,
        initialReviewDate = viewModel.initialReviewDate
    )

    Scaffold(
        topBar = {
            NewReviewScreenTopAppBar(
                onNavBack = onNavBack,
                onConfirmReview = onConfirmReview
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NewReviewScreenContent(screenState)
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
    screenState: NewReviewScreenState
) {
    if (screenState.shouldShowRatingDialog) {
        RatingPickerDialog(
            onDismiss = {  screenState.dismissRatingDialog() },
            onConfirm = { newRating ->
                screenState.dismissRatingDialog()
                screenState.setRating(newRating)
            }
        )
    }

    if (screenState.shouldShowDateDialog) {
        ReviewDatePickerDialog(
            initialReviewDate = screenState.reviewDate,
            onConfirm = {
                screenState.dismissDateDialog()
                screenState.setReviewDate(it)
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
        Text("title")
        TextField(value = screenState.mediaTitle, onValueChange = {
            screenState.mediaTitle = it
        })

        Text("rating")
        Button(onClick = { screenState.showRatingDialog() }) {
            Text(screenState.ratingString)
        }

        Text("date")
        Button(onClick = { screenState.showDateDialog() }) {
            Text(screenState.reviewDateString)
        }

        Text("review")
        TextField(
            value = screenState.userReview,
            onValueChange = { screenState.userReview = it },
            minLines = 2
        )

        Text("where did you watch this?")
        Button(onClick = { /*TODO*/ }) {
            Text("add context")
        }
    }
}


@Preview
@Composable
fun PreviewNewReviewScreen() {
    NewReviewScreen(
        onNavBack = {},
        onConfirmReview = {}
    )
}