package com.rbraithwaite.untitledmovieapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "main"
            ) {
                composable(route = "main") {
                    MainScreen(
                        beginAddReviewFlow = {
                            navController.navigate(route = "add_review_flow")
                        }
                    )
                }

                // 'add review' flow
                navigation(startDestination = "search", route = "add_review_flow") {
                    composable(route = "search") {
                        PlaceholderScreen(text = "dis be the search screen!", color = Color.Green)
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(text: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

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

@Composable
fun MainBottomNavBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { /*TODO*/ },
            icon = {
                Icon(Icons.Filled.DateRange, contentDescription = "what")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {
                Icon(Icons.Filled.AccountCircle, contentDescription = "what")
            }
        )
    }
}