package com.rbraithwaite.yourmovielog.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun QuickSearchMultiFields(
    searchInput: QuickSearch.Multi
) {
    TextField(
        value = searchInput.query,
        onValueChange = {
            searchInput.onChangeQuery(it)
        },
        label = {
            Text(text = "Name of a movie, tv show, or person")
        },
        modifier = Modifier.fillMaxWidth()
    )
}