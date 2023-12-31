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
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainBottomNavBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "what")
            },
            label = {
                Text("home")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
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
    MainBottomNavBar()
}