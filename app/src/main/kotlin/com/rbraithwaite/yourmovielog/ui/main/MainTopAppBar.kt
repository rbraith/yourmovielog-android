package com.rbraithwaite.yourmovielog.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    mainState: MainState
) {
    TopAppBar(
        title = { Text("Your Movie Log") },
        navigationIcon = {
            IconButton(onClick = {
                mainState.openDrawer()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        }
    )
}
