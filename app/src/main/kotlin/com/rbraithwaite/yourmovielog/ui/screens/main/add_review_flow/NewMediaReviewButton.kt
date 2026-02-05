package com.rbraithwaite.yourmovielog.ui.screens.main.add_review_flow

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbraithwaite.yourmovielog.ui.screens.search.QuickSearch
import com.rbraithwaite.yourmovielog.ui.screens.search.SearchInputUiState

@Composable
fun NewMediaReviewButton(
    state: SearchInputUiState,
    onClickAddReview: (mediaTitle: String) -> Unit
) {
    val searchInput = state.searchInput
    val mediaTitle = when (searchInput) {
        is QuickSearch.Multi -> searchInput.query
        else -> "NOT IMPLEMENTED"
    }

    Button(
        onClick = {
            onClickAddReview(mediaTitle)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text("Add review for \"$mediaTitle\"")
    }
}
