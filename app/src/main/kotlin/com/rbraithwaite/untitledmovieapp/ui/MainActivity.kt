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
import com.google.gson.Gson
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
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
                            onNavToNewReviewScreen = { searchResult ->
                                val args = serializeForNav(searchResult)
                                navController.navigate(route = "new_review/$args")
                            }
                        )
                    }

                    composable(route = "new_review/{media_type}/{media_data}") { navBackStackEntry ->
                        val media = deserializeNewReviewArgs(navBackStackEntry.arguments)
                        NewReviewScreen(media)
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
        is NewReviewSearchResult.CustomMedia -> {
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
        "custom" -> gson.fromJson(mediaData, NewReviewSearchResult.CustomMedia::class.java)
        else -> null
    }
}