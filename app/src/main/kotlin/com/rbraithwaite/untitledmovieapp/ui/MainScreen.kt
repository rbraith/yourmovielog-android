package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(
    beginAddReviewFlow: () -> Unit
) {
    Scaffold(
        bottomBar = {
            MainBottomNavBar()
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add Review") },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                onClick = beginAddReviewFlow)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            PlaceholderScreen(text = "dis be the main content", color = Color.Yellow)
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(
        beginAddReviewFlow = {}
    )
}