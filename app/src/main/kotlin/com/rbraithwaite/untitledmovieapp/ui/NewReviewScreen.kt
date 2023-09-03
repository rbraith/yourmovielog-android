package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder

@Composable
fun NewReviewScreen(
    media: NewReviewSearchResult?
) {
    val titleString = when(media) {
        is NewReviewSearchResult.CustomMedia -> {
            media.title
        }
        null -> "no title"
    }

    DebugPlaceholder(
        label = titleString,
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun PreviewNewReviewScreen() {
    NewReviewScreen(null)
}