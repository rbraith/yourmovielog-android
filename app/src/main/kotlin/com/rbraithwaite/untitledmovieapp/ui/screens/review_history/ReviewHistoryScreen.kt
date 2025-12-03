package com.rbraithwaite.untitledmovieapp.ui.screens.review_history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor

@Composable
fun ReviewHistoryScreen(
    viewModel: ReviewHistoryViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (val uiStateValue = uiState.value) {
        is ReviewHistoryUiState.Loading -> {
            Text("Loading")
        }
        is ReviewHistoryUiState.Success -> {
            ReviewHistoryList(uiState = uiStateValue)
        }
    }
}

@Composable
fun ReviewHistoryList(uiState: ReviewHistoryUiState.Success) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        items(uiState.reviews, key = { it.id }) { review ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .randomBackgroundColor()
                .padding(16.dp)) {
                // rating
                Text(
                    text = review.formatRating(),
                    fontSize = 26.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(24.dp))

                // media info
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text =review.formatMediaType(),
                    )

                    Text(
                        text = review.formatMediaTitle(),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

// REFACTOR [24-01-7 8:07p.m.] -- I guess I should move all this formatting stuff into the viewmodel.
private fun Review.formatRating(): String {
    if (this.rating == null) {
        return "---"
    }

    val tens = this.rating / 10
    val remainder = this.rating % 10
    return "${tens}.${remainder}"
}

private fun Review.formatMediaType(): String {
    val relatedMedia = getExtra<Review.Extras.RelatedMedia>() ?: return "?"

    return when (relatedMedia) {
        is Review.Extras.RelatedMedia.Tmdb -> {
            when (relatedMedia.data) {
                is TmdbLite.Movie -> "tmdb movie"
                else -> {
                    // TO IMPLEMENT
                    TODO("other tmdb types beside movie not implemented yet.")
                }
            }
        }
    }
}

private fun Review.formatMediaTitle(): String {
    val relatedMedia = getExtra<Review.Extras.RelatedMedia>() ?: return "??"

    return when (relatedMedia) {
        is Review.Extras.RelatedMedia.Tmdb -> {
            when (val media = relatedMedia.data) {
                is TmdbLite.Movie -> media.title
                else -> {
                    // TO IMPLEMENT
                    TODO("other tmdb types beside movie not implemented yet.")
                }
            }
        }
    }
}