package com.rbraithwaite.untitledmovieapp.ui.screen_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor
import timber.log.Timber

sealed interface NewReviewSearchResult {
    data class NewCustomMedia(
        val title: String
    ) : NewReviewSearchResult
}

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavToNewReviewScreen: (NewReviewSearchResult) -> Unit
) {
    val searchInput by viewModel.searchInput.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        item {
            SearchInputWidget(
                searchInput = searchInput,
                onSwitchInputMode = {
                    // TO IMPLEMENT
                    Timber.d("SearchInputWidget onSwitchInputMode")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Divider(color = Color.Red)
        }

        SearchResults(
            searchInput = searchInput,
            onSelectResult = {
                onNavToNewReviewScreen(it)
            }
        )
    }
}

enum class SearchInputMode {
    QUICK, ADVANCED
}

@Composable
fun SearchInputWidget(
    searchInput: SearchInput,
    onSwitchInputMode: (SearchInputMode) -> Unit,
    modifier: Modifier)
= when(searchInput) {
    is SearchInput.Quick -> {
        QuickSearchWidget(
            searchInputText = searchInput.input,
            onSearchInputChange = { searchInput.updateInput(it) },
            onGoToAdvancedSearch = { onSwitchInputMode(SearchInputMode.ADVANCED) },
            modifier = modifier
        )
    }
}

@Composable
fun QuickSearchWidget(
    searchInputText: String,
    onSearchInputChange: (String) -> Unit,
    onGoToAdvancedSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Quick Search",
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        TextField(
            value = searchInputText,
            onValueChange = {
                onSearchInputChange(it)
            },
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = onGoToAdvancedSearch,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 8.dp, end = 8.dp)
        ) {
            Text(text = "Advanced Search")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}

fun LazyListScope.SearchResults(
    searchInput: SearchInput,
    onSelectResult: (NewReviewSearchResult) -> Unit
) {
    item {
        Text("Results")
    }

    item {
        NewCustomMediaSearchResult(
            searchInput = searchInput,
            onSelect = { onSelectResult(it) }
        )
    }
}

@Composable
fun NewCustomMediaSearchResult(
    searchInput: SearchInput,
    onSelect: (NewReviewSearchResult.NewCustomMedia) -> Unit
) {
    val newCustomMediaTitle: String = remember(searchInput) {
        when (searchInput) {
            is SearchInput.Quick -> searchInput.input
        }
    }

    Button(
        onClick = {
            onSelect(NewReviewSearchResult.NewCustomMedia(newCustomMediaTitle))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text("Add review for \"$newCustomMediaTitle\"")
    }
}