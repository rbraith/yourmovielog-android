package com.rbraithwaite.untitledmovieapp.ui.screen_main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.tooling.preview.Preview

enum class BottomNavDest(val route: String) {
    HOME("home"),
    REVIEW_HISTORY("review_history")
}

@Composable
fun MainBottomNavBar(
    initialDest: String,
    onSelectDest: (BottomNavDest) -> Unit
) {
    var selectedDest by remember(initialDest) { mutableStateOf(initialDest) }

    NavigationBar {
        NavigationBarItem(
            selected = selectedDest == "home",
            onClick = {
                // REFACTOR [24-12-31 6:05p.m.] -- I should do that android guide thing of
                //  defining the bottom bar item values in an enum, then loop over it. everything
                //  with the nav is hardcoded rn.
                selectedDest = BottomNavDest.HOME.route
                onSelectDest(BottomNavDest.HOME)
            },
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "what")
            },
            label = {
                Text("home")
            }
        )
        NavigationBarItem(
            selected = selectedDest == "review_history",
            onClick = {
                selectedDest = BottomNavDest.REVIEW_HISTORY.route
                onSelectDest(BottomNavDest.REVIEW_HISTORY)
            },
            icon = {
                Icon(Icons.Filled.Star, contentDescription = "what")
            },
            label = {
                Text("reviews")
            }
        )
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