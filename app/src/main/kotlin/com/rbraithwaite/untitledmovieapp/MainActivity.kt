package com.rbraithwaite.untitledmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "what") },
                            label = { Text("dest 1") },
                            selected = currentDestination?.hierarchy?.any { it.route == "dest1" } == true,
                            onClick = {
                                navController.navigate("dest1") {
                                    // pop to start dest of the nav graph, to stop from building a large back stack
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // avoid multiple copies of same dest when reselecting
                                    launchSingleTop = true
                                    // restore state when selecting previously selected item
                                    restoreState = true
                                }
                            }
                        )

                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.DateRange, contentDescription = "what") },
                            label = { Text("dest 2") },
                            selected = currentDestination?.hierarchy?.any { it.route == "dest2" } == true,
                            onClick = {
                                navController.navigate("dest2") {
                                    // pop to start dest of the nav graph, to stop from building a large back stack
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // avoid multiple copies of same dest when reselecting
                                    launchSingleTop = true
                                    // restore state when selecting previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "dest1",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("dest1") {
                        PlaceholderScreen(text = "this is the first screen", color = Color.Cyan)
                    }
                    composable("dest2") {
                        PlaceholderScreen(text = "this is the second screen", color = Color.Yellow)
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(text: String, color: Color) {
    Box(
        modifier = Modifier.fillMaxSize().background(color)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}