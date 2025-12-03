package com.rbraithwaite.untitledmovieapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rbraithwaite.untitledmovieapp.ui.screens.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                route = "root_nav",
                startDestination = "main"
            ) {
                composable(route = "main") {
                    MainScreen()
                }

                // 'add review' flow
//                navigation(startDestination = "search", route = "add_review_flow") {
//                    composable(route = "search") {
//                        val addReviewFlowSharedData = navController.getScopedViewModel<AddReviewFlowSharedData>(
//                            rememberKey = it,
//                            scopeRoute = "add_review_flow"
//                        )
//
//                        // TODO [24-02-2 12:14a.m.] broken.
////                        SearchScreen(
////                            hiltViewModel(),
////                            onNavToNewReviewScreen = { searchResult ->
////                                addReviewFlowSharedData.newReviewSearchResult = searchResult
////                                navController.navigate(route = "new_review")
////                            }
////                        )
//                    }
//
//                    composable(route = "new_review") { navBackStackEntry ->
//                        val addReviewFlowSharedData = navController.getScopedViewModel<AddReviewFlowSharedData>(
//                            rememberKey = navBackStackEntry,
//                            scopeRoute = "add_review_flow"
//                        )
//
//                        val viewModel = hiltViewModel<NewReviewViewModel>()
//
//                        // TODO [24-02-2 12:14a.m.] broken.
////                        val viewModelInitialized = waitFor(navBackStackEntry) {
////                            viewModel.init(addReviewFlowSharedData.newReviewSearchResult)
////                        }
////
////                        if (viewModelInitialized) {
////                            NewReviewScreen(
////                                viewModel = viewModel,
////                                onConfirmReview = {
////                                    // TO IMPLEMENT
////                                },
////                                onNavBack = {
////                                    // TO IMPLEMENT
////                                }
////                            )
////                        }
//                    }
//                }
            }
        }
    }
}