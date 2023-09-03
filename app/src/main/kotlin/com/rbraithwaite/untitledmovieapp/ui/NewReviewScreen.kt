package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        Text("title")
        TextField(value = titleString, onValueChange = {})

        Text("rating")
        Button(onClick = { /*TODO*/ }) {
            Text("3.4 / 10")
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

@Preview
@Composable
fun PreviewNewReviewScreen() {
    NewReviewScreen(null)
}