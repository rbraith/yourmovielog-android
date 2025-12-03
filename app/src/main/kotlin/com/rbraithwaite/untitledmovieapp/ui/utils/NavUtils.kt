package com.rbraithwaite.untitledmovieapp.ui.utils

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

/**
 * Use the returned boolean state to wait until the given codeblock has completed
 */
@Composable
inline fun sequentialEffect(rememberKey: Any? = null, crossinline codeBlock: () -> Unit): Boolean {
    var isFinished by remember(rememberKey) { mutableStateOf(false) }
    LaunchedEffect(rememberKey) {
        codeBlock()
        isFinished = true
    }
    return isFinished
}