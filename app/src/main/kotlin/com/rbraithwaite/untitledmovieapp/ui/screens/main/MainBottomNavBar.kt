package com.rbraithwaite.untitledmovieapp.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

enum class BottomNavDest(
    val route: String,
    // REFACTOR [24-01-4 10:24p.m.] -- label should be a string resource id instead.
    val label: String,
    val icon: ImageVector
) {
    HOME(
        route = "home",
        label = "Home",
        icon = Icons.Filled.Home
    ),
    REVIEW_HISTORY(
        route = "review_history",
        label = "Reviews",
        icon = Icons.Filled.Star
    )
}

@Composable
fun MainBottomNavBar(
    initialDest: String,
    onSelectDest: (String) -> Unit
) {
    var selectedDest by remember(initialDest) { mutableStateOf(initialDest) }

    NavigationBar {
        for (dest in BottomNavDest.entries) {
            NavigationBarItem(
                selected = selectedDest == dest.route,
                icon = {
                    Icon(imageVector = dest.icon, contentDescription = null)
                },
                label = { Text(dest.label) },
                onClick = {
                    selectedDest = dest.route
                    onSelectDest(dest.route)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewMainBottomNavBar() {
    MainBottomNavBar(
        initialDest = "home",
        onSelectDest = {}
    )
}