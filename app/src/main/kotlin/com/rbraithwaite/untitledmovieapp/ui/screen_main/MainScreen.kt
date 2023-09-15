package com.rbraithwaite.untitledmovieapp.ui.screen_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder

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
            DebugPlaceholder(
                label = "main content!",
                modifier = Modifier.fillMaxSize()
            )
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