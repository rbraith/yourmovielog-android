package com.rbraithwaite.yourmovielog.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainNavDrawer(
    mainState: MainState
) {
    ModalDrawerSheet(
        modifier = Modifier.width(320.dp)
    ) {
        MainNavDrawerItem(
            label = "Add Review",
            dest = MainDrawerDest.ADD_REVIEW_FLOW,
            mainState = mainState
        )
        MainNavDrawerItem(
            label = "Search",
            dest = MainDrawerDest.SEARCH,
            mainState = mainState
        )
    }
}

// TODO [25-10-23 2:53p.m.] consider hoisting onClick and selected.
@Composable
private fun MainNavDrawerItem(
    label: String,
    dest: MainDrawerDest,
    mainState: MainState
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = mainState.isDrawerDestSelected(dest),
        onClick = {
            mainState.navigateFromDrawerTo(dest)
        },
        modifier = Modifier.padding(8.dp)
    )
}

enum class MainDrawerDest(val route: String) {
    ADD_REVIEW_FLOW("add-review-flow"),
    SEARCH("search")
}
