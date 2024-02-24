package com.rbraithwaite.untitledmovieapp.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
import com.rbraithwaite.untitledmovieapp.ui.screens.home.HomeScreen
import com.rbraithwaite.untitledmovieapp.ui.screens.review_history.ReviewHistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(320.dp)
            ) {
                NavigationDrawerItem(
                    label = { Text("Add Review")},
                    selected = true,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Search") },
                    selected = false,
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Tmdb Movie App") },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            DebugPlaceholder(
                label = "Scaffold content",
                modifier = Modifier.fillMaxSize().padding(it)
            )
        }
    }

//    val navController = rememberNavController()
//
//    Scaffold(
//        bottomBar = {
//            MainBottomNavBar(
//                initialDest = startDest,
//                onSelectDest = { selectedDest ->
//                    navController.navigate(selectedDest) {
//                        // pop up to start to prevent a large dest backstack from building
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            saveState = true
//                        }
//
//                        // prevent selecting the same dest twice
//                        launchSingleTop = true
//
//                        // restore state when selecting previously selected item
//                        restoreState = true
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = startDest,
//            modifier = Modifier.padding(innerPadding)
//        ) {
//            composable(route = BottomNavDest.HOME.route) {
//                HomeScreen(
//                    beginAddReviewFlow = beginAddReviewFlow
//                )
//            }
//
//            composable(route = BottomNavDest.REVIEW_HISTORY.route) {
//                ReviewHistoryScreen(hiltViewModel())
//            }
//        }
//    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}