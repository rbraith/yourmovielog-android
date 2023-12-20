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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.rbraithwaite.untitledmovieapp.AddReviewFlowSharedData
import com.rbraithwaite.untitledmovieapp.ui.screen_main.MainScreen
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.NewReviewScreen
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchScreen
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
                    MainScreen(
                        beginAddReviewFlow = {
                            navController.navigate(route = "add_review_flow")
                        }
                    )
                }

                // 'add review' flow
                navigation(startDestination = "search", route = "add_review_flow") {
                    composable(route = "search") {
                        val addReviewFlowSharedData = navController.getScopedViewModel<AddReviewFlowSharedData>(
                            rememberKey = it,
                            scopeRoute = "add_review_flow"
                        )

                        SearchScreen(
                            hiltViewModel(),
                            onNavToNewReviewScreen = { searchResult ->
                                addReviewFlowSharedData.newReviewSearchResult = searchResult
                                navController.navigate(route = "new_review")
                            }
                        )
                    }

                    composable(route = "new_review") { navBackStackEntry ->
                        val addReviewFlowSharedData = navController.getScopedViewModel<AddReviewFlowSharedData>(
                            rememberKey = navBackStackEntry,
                            scopeRoute = "add_review_flow"
                        )

                        val viewModel = hiltViewModel<NewReviewViewModel>()

                        val viewModelInitialized = waitFor(navBackStackEntry) {
                            viewModel.init(addReviewFlowSharedData.newReviewSearchResult)
                        }

                        if (viewModelInitialized) {
                            NewReviewScreen(
                                viewModel = viewModel,
                                onConfirmReview = {
                                    // TO IMPLEMENT
                                },
                                onNavBack = {
                                    // TO IMPLEMENT
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// REFACTOR [23-12-20 1:28a.m.] -- move to utils.
// REFACTOR [23-12-20 1:28a.m.] -- find a better name.
@Composable
inline fun waitFor(rememberKey: Any? = null, crossinline codeBlock: () -> Unit): Boolean {
    var isFinished by remember(rememberKey) { mutableStateOf(false) }
    LaunchedEffect(rememberKey) {
        codeBlock()
        isFinished = true
    }
    return isFinished
}

// REFACTOR [23-12-20 1:28a.m.] -- move to utils.
/**
 * @param rememberKey This is used to remember the scope backstack entry
 * @param scopeRoute The route to scope the returned ViewModel to.
 */
@Composable
inline fun <reified T : ViewModel> NavHostController.getScopedViewModel(
    rememberKey: NavBackStackEntry,
    scopeRoute: String
): T {
    val scopeEntry = remember(rememberKey) {
        this.getBackStackEntry(scopeRoute)
    }
    return hiltViewModel(scopeEntry)
}