package com.rbraithwaite.yourmovielog.ui.screens.review_history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.yourmovielog.ui.debug.DebugPlaceholder
import com.rbraithwaite.yourmovielog.ui.main.MainScaffold
import com.rbraithwaite.yourmovielog.ui.main.MainState

@Composable
fun ReviewHistoryScreen(
    viewModel: ReviewHistoryViewModel,
    mainState: MainState
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    MainScaffold(mainState) {
        when (val state = uiState.value) {
            is ReviewHistoryUiState.Loading -> {
                DebugPlaceholder(
                    label = "Loading...",
                    modifier = Modifier.fillMaxSize()
                )
            }
            is ReviewHistoryUiState.Success -> {
                LazyColumn {
                    items(state.reviews, key = { review -> review.uuid }) { review ->
                        DebugPlaceholder(
                            label = review.toString(),
                            modifier = Modifier.fillMaxWidth().height(60.dp)
                        )
                    }
                }
            }
        }
    }
}
