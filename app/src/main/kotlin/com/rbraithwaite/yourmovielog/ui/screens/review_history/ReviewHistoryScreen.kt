package com.rbraithwaite.yourmovielog.ui.screens.review_history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.MediaReviewWithMedia
import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.TvShow
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
                    items(state.reviews, key = { review -> review.value.uuid }) { review ->
                        ReviewHistoryListItem(review)
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewHistoryListItem(review: MediaReviewWithMedia) {
    Column {
        Text(review.media.formattedTitle)
        Text(review.value.rating.toString()) // TODO [26-02-12 4:52p.m.] format the rating.
        Text(review.value.review ?: "No review", overflow = TextOverflow.Ellipsis, maxLines = 1)
        review.value.watchContext?.let { watchContext ->
            Text(watchContext)
        }
    }
}

private val Media.formattedTitle: String
    get() = when (this) {
        is Movie -> this.title
        is TvShow -> this.title
        else -> TODO("not yet implemented")
    }
