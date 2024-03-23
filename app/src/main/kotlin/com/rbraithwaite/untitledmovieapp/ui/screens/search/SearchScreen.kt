package com.rbraithwaite.untitledmovieapp.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
import kotlin.reflect.KClass


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    // TODO [24-02-2 12:15a.m.] broken.
//    onNavToNewReviewScreen: (SearchResult) -> Unit
) {
    val searchInputUiState by viewModel.searchInputUiState.collectAsStateWithLifecycle()

    // TODO [24-03-23 6:42p.m.] -- .
//    val searchResultsUiState by viewModel.searchResultsUiState.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            SearchInputWidget(searchInputUiState)
        }

        item {
            NewCustomMediaButton(title = "TEMP hardcoded media")
        }

        // Results header
        item {
            Text(
                text  = "Results",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun NewCustomMediaButton(
    title: String,
) {
    Button(
        onClick = {
                  // TODO [24-03-23 6:44p.m.] -- .
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Text("Add review for \"$title\"")
    }
}


@Composable
private fun SearchInputWidget(
    state: SearchInputUiState
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        SearchInputModeSelector(
            searchInput = state.searchInput,
            onSearchInputModeSelected = {
                state.onChangeSearchInputType(it)
            }
        )

        // Search input fields container, to control the margins
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(top = 16.dp) // give the top a little more padding separately
        ) {
            // Input fields
            when (state.searchInput) {
                is QuickSearch.Multi -> QuickSearchMultiFields(searchInput = state.searchInput)
                else -> {
                    DebugPlaceholder(
                        label = state.searchInput::class.simpleName ?: "wip",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SearchButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.End)
            )

        }
    }
}

@Composable
private fun SearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            Icons.Filled.Search,
            contentDescription = null
        )

        Text("Search")
    }
}












//enum class SearchInputMode {
//    QUICK, ADVANCED
//}
//
//@Composable
//fun SearchInputWidget(
//    searchInput: SearchInput,
//    onSwitchInputMode: (SearchInputMode) -> Unit,
//    modifier: Modifier)
//= when(searchInput) {
//    is SearchInput.Quick -> {
//        QuickSearchWidget(
//            searchInputText = searchInput.input,
//            onSearchInputChange = { searchInput.updateInput(it) },
//            onGoToAdvancedSearch = { onSwitchInputMode(SearchInputMode.ADVANCED) },
//            modifier = modifier
//        )
//    }
//}
//
//@Composable
//fun QuickSearchWidget(
//    searchInputText: String,
//    onSearchInputChange: (String) -> Unit,
//    onGoToAdvancedSearch: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier
//    ) {
//        Text(
//            text = "Quick Search",
//            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
//        )
//        TextField(
//            value = searchInputText,
//            onValueChange = {
//                onSearchInputChange(it)
//            },
//            modifier = Modifier
//                .padding(vertical = 4.dp, horizontal = 8.dp)
//                .fillMaxWidth()
//        )
//        Button(
//            onClick = onGoToAdvancedSearch,
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(bottom = 8.dp, end = 8.dp)
//        ) {
//            Text(text = "Advanced Search")
//            Spacer(modifier = Modifier.width(4.dp))
//            Icon(
//                Icons.Filled.ArrowForward,
//                contentDescription = null
//            )
//        }
//    }
//}
//
//@Composable
//private fun ResultItemNoInput() {
//    Text("Waiting for search input")
//}
//
//@Composable
//private fun ResultItemLoading() {
//    Text("Loading...")
//}
//

//
//fun LazyListScope.SearchResults(
//    searchResults: SearchResults,
//    // TODO [24-02-2 12:16a.m.] broken.
////    onSelectResult: (SearchResult) -> Unit
//) = when (searchResults) {
//    is SearchResults.NoInput -> {
//        item {
//            ResultItemNoInput()
//        }
//    }
//    is SearchResults.Loading -> {
//        item {
//            ResultItemNewCustomMedia(
//                title = searchResults.newCustomMediaTitle,
//                // TODO [24-02-2 12:16a.m.] broken.
////                onSelect = { onSelectResult(it) }
//            )
//        }
//        item {
//            ResultItemLoading()
//        }
//    }
//    is SearchResults.Success -> {
//        item {
//            // REFACTOR [23-10-21 3:44p.m.] -- duplicated in SearchResults.Loading type.
//            ResultItemNewCustomMedia(
//                title = searchResults.newCustomMediaTitle,
//                // TODO [24-02-2 12:16a.m.] broken.
////                onSelect = { onSelectResult(it) }
//            )
//        }
//        // TODO [24-02-2 12:16a.m.] broken.
////        items(
////            items = searchResults.searchResults,
////            key = {
////                when (it) {
////                    is SearchResult.CustomMedia -> "custom/${it.data.id}"
////                    is SearchResult.TmdbMovie -> "movie/${it.data.id}"
////                    is SearchResult.TmdbTvShow -> "tv_show/${it.data.id}"
////                    is SearchResult.TmdbPerson -> "person/${it.data.id}"
////                }
////            }
////        ) { searchResult ->
////            Surface(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(30.dp)
////                    .randomBackgroundColor()
////                    .clickable {
////                        onSelectResult(searchResult)
////                    }
////            ) {
////                when (searchResult) {
////                    is SearchResult.CustomMedia -> ResultItemContentCustomMedia(searchResult)
////                    is SearchResult.TmdbMovie -> ResultItemContentTmdbMovie(searchResult)
////                    is SearchResult.TmdbTvShow -> ResultItemContentTmdbTvShow(searchResult)
////                    // TODO [23-12-17 12:20a.m.] -- get rid of TmdbPerson results, these don't
////                    //  work for adding new reviews.
////                    is SearchResult.TmdbPerson -> ResultItemContentTmdbPerson(searchResult)
////                }
////            }
////        }
//    }
//}

// TODO [24-02-2 12:16a.m.] broken.
//@Composable
//private fun ResultItemContentCustomMedia(result: SearchResult.CustomMedia) {
//    Text("custom media: ${result.data.title}")
//}
//
//@Composable
//private fun ResultItemContentTmdbMovie(result: SearchResult.TmdbMovie) {
//    Text("tmdb movie: ${result.data.title}")
//}
//
//@Composable
//private fun ResultItemContentTmdbTvShow(result: SearchResult.TmdbTvShow) {
//    Text("tmdb show: ${result.data.name}")
//}
//
//@Composable
//private fun ResultItemContentTmdbPerson(result: SearchResult.TmdbPerson) {
//    Text("tmdb person: ${result.data.name}")
//}