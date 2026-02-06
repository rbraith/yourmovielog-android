package com.rbraithwaite.yourmovielog.ui.screens.review_history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rbraithwaite.yourmovielog.ui.debug.DebugPlaceholder
import com.rbraithwaite.yourmovielog.ui.main.MainScaffold
import com.rbraithwaite.yourmovielog.ui.main.MainState

@Composable
fun ReviewHistoryScreen(mainState: MainState) {
    MainScaffold(mainState) {
        DebugPlaceholder(
            label = "TODO review history screen",
            modifier = Modifier.fillMaxSize()
        )
    }
}
