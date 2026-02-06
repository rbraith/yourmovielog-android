package com.rbraithwaite.yourmovielog.ui.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO [25-10-23 2:51p.m.] the initial selected drawer dest should be a param
//  ...does a dynamic initial stat val like that even work with remember?
@Composable
fun rememberMainState(): MainState {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val selectedDrawerDest = remember { mutableStateOf(MainDrawerDest.ADD_REVIEW_FLOW) }

    return remember {
        MainState(
            drawerState,
            navController,
            coroutineScope,
            selectedDrawerDest
        )
    }
}

class MainState(
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
