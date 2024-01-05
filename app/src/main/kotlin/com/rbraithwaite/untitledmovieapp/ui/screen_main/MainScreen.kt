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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rbraithwaite.untitledmovieapp.DebugLogging
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder

@Composable
fun MainScreen(
    startDest: String = "home",
    beginAddReviewFlow: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainBottomNavBar(
                initialDest = startDest,
                onSelectDest = { selectedDest ->
                    navController.navigate(selectedDest.route) {
                        // pop up to start to prevent a large dest backstack from building
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        // prevent selecting the same dest twice
                        launchSingleTop = true

                        // restore state when selecting previously selected item
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDest,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavDest.HOME.route) {
                HomeScreen(
                    beginAddReviewFlow = beginAddReviewFlow
                )
            }

            composable(route = BottomNavDest.REVIEW_HISTORY.route) {
                DebugPlaceholder(
                    label = "review screen!",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

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

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(
        beginAddReviewFlow = {}
    )
}