package com.rbraithwaite.yourmovielog.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rbraithwaite.yourmovielog.ui.debug.DebugPlaceholder
import com.rbraithwaite.yourmovielog.ui.main.add_review_flow.AddReviewFlowDest
import com.rbraithwaite.yourmovielog.ui.main.add_review_flow.AddReviewFlowSharedData
import com.rbraithwaite.yourmovielog.ui.main.add_review_flow.NewMediaReviewButton
import com.rbraithwaite.yourmovielog.ui.screens.new_review.NewReviewArgs
import com.rbraithwaite.yourmovielog.ui.screens.new_review.NewReviewScreen
import com.rbraithwaite.yourmovielog.ui.screens.new_review.NewReviewViewModel
import com.rbraithwaite.yourmovielog.ui.screens.search.SearchScreen
import com.rbraithwaite.yourmovielog.ui.utils.getScopedViewModel

@Composable
fun MainNavHost(
    mainState: MainState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = mainState.navController,
        startDestination = MainDrawerDest.ADD_REVIEW_FLOW.route,
        modifier = modifier
    ) {
        // Add Review Flow
        navigation(
            startDestination = AddReviewFlowDest.SEARCH.route,
            route = MainDrawerDest.ADD_REVIEW_FLOW.route
        ) {
            composable(route = AddReviewFlowDest.SEARCH.route) {
                val addReviewFlowSharedData = mainState.navController.getScopedViewModel<AddReviewFlowSharedData>(
                    it,
                    MainDrawerDest.ADD_REVIEW_FLOW.route
                )

                MainScaffold(mainState) {
                    SearchScreen(
                        hiltViewModel(),
                        preResultsSlot = { searchInputUiState ->
                            // A button to add a review for a new media, if the user can't find anything
                            // in the search results
                            NewMediaReviewButton(searchInputUiState) { mediaTitle ->
                                addReviewFlowSharedData.newReviewArgs = NewReviewArgs.NewMedia(mediaTitle)
                                mainState.navController.navigate(
                                    route = AddReviewFlowDest.ADD_REVIEW.route,
                                )
                            }
                        }
                    )
                }
            }

            composable(route = AddReviewFlowDest.ADD_REVIEW.route) {
                val addReviewFlowSharedData = mainState.navController.getScopedViewModel<AddReviewFlowSharedData>(
                    it,
                    MainDrawerDest.ADD_REVIEW_FLOW.route
                )

                val viewModel: NewReviewViewModel = hiltViewModel()
                viewModel.init(addReviewFlowSharedData.newReviewArgs)

                NewReviewScreen(
                    viewModel = viewModel,
                    onNavBack = {
                        mainState.navController.navigateUp()
                    },
                    onConfirmReview = {
                        // navigate back to the start of the "add review" flow after adding a new review
                        mainState.navController.navigate(MainDrawerDest.ADD_REVIEW_FLOW.route) {
                            popUpTo(MainDrawerDest.ADD_REVIEW_FLOW.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(route = MainDrawerDest.SEARCH.route) {
            DebugPlaceholder(
                label = "Search Screen",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
