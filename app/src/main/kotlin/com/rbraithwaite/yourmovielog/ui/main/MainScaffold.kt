package com.rbraithwaite.yourmovielog.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScaffold(
    mainState: MainState,
    content: @Composable (() -> Unit)
) {
    Scaffold(
        topBar = {
            MainTopAppBar(mainState = mainState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}
