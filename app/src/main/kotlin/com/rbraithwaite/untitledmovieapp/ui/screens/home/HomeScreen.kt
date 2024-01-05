package com.rbraithwaite.untitledmovieapp.ui.screens.home

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
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder

@Composable
fun HomeScreen(
    beginAddReviewFlow: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add Review") },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                onClick = beginAddReviewFlow)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            DebugPlaceholder(
                label = "home content!",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}