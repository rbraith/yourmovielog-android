package com.rbraithwaite.untitledmovieapp.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewArgs
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewScreen
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewViewModel
import com.rbraithwaite.untitledmovieapp.ui.screens.search.SearchScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class MainDrawerDest(val route: String) {
    ADD_REVIEW_FLOW("add-review-flow"),
    SEARCH("search")
}

enum class AddReviewFlowDest(val route: String) {
    SEARCH("search"),
    ADD_REVIEW("add-review/{arg-type}/{arg-value}")
}

class MainScreenState(
    val drawerState: DrawerState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
    private val selectedDrawerDest: MutableState<MainDrawerDest>
) {
    fun navigateFromDrawerTo(dest: MainDrawerDest) {
        navController.navigateFromDrawerTo(dest)
        selectedDrawerDest.value = dest
        // TODO [25-10-23 2:50p.m.] make this a public fun 'closeDrawer'.
        coroutineScope.launch {
            drawerState.close()
        }
    }

    fun isDrawerDestSelected(dest: MainDrawerDest): Boolean {
        return dest == selectedDrawerDest.value
    }

    fun openDrawer() {
        coroutineScope.launch {
            drawerState.open()
        }
    }
}

// TODO [25-10-23 2:51p.m.] the initial selected drawer dest should be a param
//  ...does a dynamic initial stat val like that even work with remember?
@Composable
private fun rememberMainScreenState(): MainScreenState {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val selectedDrawerDest = remember { mutableStateOf(MainDrawerDest.ADD_REVIEW_FLOW) }

    return remember {
        MainScreenState(
            drawerState,
            navController,
            coroutineScope,
            selectedDrawerDest
        )
    }
}

@Composable
fun MainScreen() {
    val mainScreenState = rememberMainScreenState()

    ModalNavigationDrawer(
        drawerState = mainScreenState.drawerState,
        drawerContent = {
            MainNavDrawer(mainScreenState = mainScreenState)
        }
    ) {
        Scaffold(
            topBar = {
                MainTopAppBar(mainScreenState = mainScreenState)
            }
        ) { innerPadding ->
            MainNavHost(
                mainScreenState = mainScreenState,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun MainNavDrawer(
    mainScreenState: MainScreenState
) {
    ModalDrawerSheet(
        modifier = Modifier.width(320.dp)
    ) {
        MainNavDrawerItem(
            label = "Add Review",
            dest = MainDrawerDest.ADD_REVIEW_FLOW,
            mainScreenState = mainScreenState
        )
        MainNavDrawerItem(
            label = "Search",
            dest = MainDrawerDest.SEARCH,
            mainScreenState = mainScreenState
        )
    }
}

// TODO [25-10-23 2:53p.m.] consider hoisting onClick and selected.
@Composable
private fun MainNavDrawerItem(
    label: String,
    dest: MainDrawerDest,
    mainScreenState: MainScreenState
) {
    NavigationDrawerItem(
        label = { Text(label)},
        selected = mainScreenState.isDrawerDestSelected(dest),
        onClick = {
            mainScreenState.navigateFromDrawerTo(dest)
        },
        modifier = Modifier.padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopAppBar(
    mainScreenState: MainScreenState
) {
    TopAppBar(
        title = { Text("Tmdb Movie App") },
        navigationIcon = {
            IconButton(onClick = {
                mainScreenState.openDrawer()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun MainNavHost(
    mainScreenState: MainScreenState,
    modifier: Modifier
) {
    NavHost(
        navController = mainScreenState.navController,
        startDestination = MainDrawerDest.ADD_REVIEW_FLOW.route,
        modifier = modifier
    ) {
        // Add Review Flow
        navigation(
            startDestination = AddReviewFlowDest.SEARCH.route,
            route = MainDrawerDest.ADD_REVIEW_FLOW.route)
        {
            composable(route = AddReviewFlowDest.SEARCH.route) {
                SearchScreen(hiltViewModel(), onNavToNewReviewScreen = { args ->
                    mainScreenState.navController.navigate(
                        route = "add-review/${args.asRouteString()}"
                    )
                })
            }

            composable(
                route = AddReviewFlowDest.ADD_REVIEW.route,
                arguments = listOf(
                    navArgument("arg-type") { type = NavType.StringType },
                    navArgument("arg-value") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val args = NewReviewArgs.fromBackStackEntry(backStackEntry)

                val viewModel: NewReviewViewModel = hiltViewModel()
                viewModel.init(args)

                NewReviewScreen(
                    viewModel = viewModel,
                    onNavBack = { TODO() },
                    onConfirmReview = { TODO() }
                )

//                var title = "NOT IMPLEMENTED"
//                when (args) {
//                    is NewReviewArgs.NewMedia -> {
//                        title = args.value
//                    }
//                }
//
//                Column {
//                    DebugPlaceholder(
//                        label = "Add Review Screen",
//                        modifier = Modifier.fillMaxWidth().height(200.dp)
//                    )
//
//                    DebugPlaceholder(
//                        label = title,
//                        modifier = Modifier.fillMaxWidth().height(200.dp)
//                    )
//                }
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

private fun NavHostController.navigateFromDrawerTo(dest: MainDrawerDest) {
    this.navigate(dest.route) {
        // pop up to start to prevent a large dest backstack from building
        popUpTo(this@navigateFromDrawerTo.graph.findStartDestination().id) {
            saveState = true
        }

        // prevent selecting the same dest twice
        launchSingleTop = true

        // restore state when selecting previously selected item
        restoreState = true
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen()
}