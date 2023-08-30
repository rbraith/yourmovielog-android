package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder

@Composable
fun SearchScreen() {
    DebugPlaceholder(
        label = "search screen!",
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}