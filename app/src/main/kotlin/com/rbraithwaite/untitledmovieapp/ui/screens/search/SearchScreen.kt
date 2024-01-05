package com.rbraithwaite.untitledmovieapp.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor
import timber.log.Timber

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavToNewReviewScreen: (SearchResult) -> Unit
) {
    val searchInput by viewModel.searchInput.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

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

        // Results header
        item {
            Text("Results")
        }

        SearchResults(
            searchResults = searchResults,
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

@Composable
private fun ResultItemNoInput() {
    Text("Waiting for search input")
}

@Composable
private fun ResultItemLoading() {
    Text("Loading...")
}

@Composable
fun ResultItemNewCustomMedia(
    title: String,
    onSelect: (SearchResult.CustomMedia) -> Unit
) {
    Button(
        onClick = {
            // REFACTOR [23-12-20 1:44a.m.] -- hardcoded 0L id for new custom media...
            onSelect(SearchResult.CustomMedia(
                id=0L,
                title=title
            ))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text("Add review for \"$title\"")
    }
}

fun LazyListScope.SearchResults(
    searchResults: SearchResults,
    onSelectResult: (SearchResult) -> Unit
) = when (searchResults) {
    is SearchResults.NoInput -> {
        item {
            ResultItemNoInput()
        }
    }
    is SearchResults.Loading -> {
        item {
            ResultItemNewCustomMedia(
                title = searchResults.newCustomMediaTitle,
                onSelect = { onSelectResult(it) }
            )
        }
        item {
            ResultItemLoading()
        }
    }
    is SearchResults.Success -> {
        item {
            // REFACTOR [23-10-21 3:44p.m.] -- duplicated in SearchResults.Loading type.
            ResultItemNewCustomMedia(
                title = searchResults.newCustomMediaTitle,
                onSelect = { onSelectResult(it) }
            )
        }
        items(
            items = searchResults.searchResults,
            key = {
                when (it) {
                    is SearchResult.CustomMedia -> "custom/${it.id}"
                    is SearchResult.TmdbMovie -> "movie/${it.id}"
                    is SearchResult.TmdbTvShow -> "tv_show/${it.id}"
                    is SearchResult.TmdbPerson -> "person/${it.id}"
                }
            }
        ) { searchResult ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .randomBackgroundColor()
                    .clickable {
                        onSelectResult(searchResult)
                    }
            ) {
                when (searchResult) {
                    is SearchResult.CustomMedia -> ResultItemContentCustomMedia(searchResult)
                    is SearchResult.TmdbMovie -> ResultItemContentTmdbMovie(searchResult)
                    is SearchResult.TmdbTvShow -> ResultItemContentTmdbTvShow(searchResult)
                    // TODO [23-12-17 12:20a.m.] -- get rid of TmdbPerson results, these don't
                    //  work for adding new reviews.
                    is SearchResult.TmdbPerson -> ResultItemContentTmdbPerson(searchResult)
                }
            }
        }
    }
}

@Composable
private fun ResultItemContentCustomMedia(result: SearchResult.CustomMedia) {
    Text("custom media: ${result.title}")
}

@Composable
private fun ResultItemContentTmdbMovie(result: SearchResult.TmdbMovie) {
    Text("tmdb movie: ${result.title}")
}

@Composable
private fun ResultItemContentTmdbTvShow(result: SearchResult.TmdbTvShow) {
    Text("tmdb show: ${result.name}")
}

@Composable
private fun ResultItemContentTmdbPerson(result: SearchResult.TmdbPerson) {
    Text("tmdb person: ${result.name}")
}