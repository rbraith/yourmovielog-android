package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor

@Composable
fun NewReviewScreen(
    media: NewReviewSearchResult?
) {
    val titleString = when(media) {
        is NewReviewSearchResult.CustomMedia -> {
            media.title
        }
        null -> ""
    }

    var shouldShowRatingDialog by remember { mutableStateOf(false) }

    if (shouldShowRatingDialog) {
        RatingPickerDialog(
            onDismiss = { shouldShowRatingDialog = false },
            onConfirm = { shouldShowRatingDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        Text("title")
        TextField(value = titleString, onValueChange = {})

        Text("rating")
        Button(onClick = { shouldShowRatingDialog = true }) {
            Text("-- / 10")
        }

        Text("date")
        Button(onClick = { /*TODO*/ }) {
            Text("Today")
        }

        Text("review")
        TextField(value = "", onValueChange = {})

        Text("where did you watch this?")
        Button(onClick = { /*TODO*/ }) {
            Text("add context")
        }
    }
}

@Composable
fun RatingPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Row {
                TextField(
                    value = "",
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "/ 10",
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Confirm")
            }
        }
    )
}

@Preview
@Composable
fun PreviewRatingPickerDialog() {
    RatingPickerDialog(
        onDismiss = {},
        onConfirm = {}
    )
}

@Preview
@Composable
fun PreviewNewReviewScreen() {
    NewReviewScreen(null)
}