package com.rbraithwaite.untitledmovieapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.rbraithwaite.untitledmovieapp.ui.screen_main.MainScreen
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.NewReviewScreen
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchScreen
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        SearchScreen(
                            hiltViewModel(),
                            onNavToNewReviewScreen = { searchResult ->
                                val args = serializeForNav(searchResult)
                                navController.navigate(route = "new_review/$args")
                            }
                        )
                    }

                    composable(route = "new_review/{media_type}/{media_data}") { navBackStackEntry ->
                        val searchResult = deserializeNewReviewArgs(navBackStackEntry.arguments)

                        val viewModel = hiltViewModel<NewReviewViewModel>()
                        viewModel.init(searchResult)

                        NewReviewScreen(
                            viewModel = viewModel,
                            onConfirmReview = { TODO() },
                            onNavBack = { TODO() }
                        )
                    }
                }
            }
        }
    }
}

// REFACTOR [23-09-3 2:01a.m.] -- probably better to use an activity-scoped viewmodel to transfer args
//  than serializing to json strings?
fun serializeForNav(newReviewSearchResult: NewReviewSearchResult): String {
    val gson = Gson()
    return when(newReviewSearchResult) {
        is NewReviewSearchResult.NewCustomMedia -> {
            "custom/${gson.toJson(newReviewSearchResult)}"
        }
    }
}

fun deserializeNewReviewArgs(args: Bundle?): NewReviewSearchResult? {
    if (args == null) {
        return null
    }

    val mediaType = args.getString("media_type")!!
    val mediaData = args.getString("media_data")!!

    val gson = Gson()

    return when(mediaType) {
        "custom" -> gson.fromJson(mediaData, NewReviewSearchResult.NewCustomMedia::class.java)
        else -> null
    }
}