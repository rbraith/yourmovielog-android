package com.rbraithwaite.untitledmovieapp.ui.screens.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            SearchInputWidget(searchInputUiState)
        }
    }



//    val searchInput by viewModel.searchInput.collectAsStateWithLifecycle()
//    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .randomBackgroundColor()
//    ) {
//        item {
//            SearchInputWidget(
//                searchInput = searchInput,
//                onSwitchInputMode = {
//                    // TO IMPLEMENT
//                    Timber.d("SearchInputWidget onSwitchInputMode")
//                },
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//
//        item {
//            Divider(color = Color.Red)
//        }
//
//        // Results header
//        item {
//            Text("Results")
//        }
//
//        SearchResults(
//            searchResults = searchResults,
////            onSelectResult = {
////                // TODO [24-02-2 12:16a.m.] broken.
//////                onNavToNewReviewScreen(it)
////            }
//        )
//    }
}


@Composable
private fun SearchInputWidget(
    state: SearchInputUiState
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        SearchInputModeSelector(
            searchInput = state.searchInput,
            onSearchInputModeSelected = {
                state.onChangeSearchInputType(it)
            }
        )

        DebugPlaceholder(
            label = "search input widget",
            modifier = Modifier.fillMaxSize()
        )
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
//@Composable
//fun ResultItemNewCustomMedia(
//    title: String,
//    // TODO [24-02-2 12:16a.m.] broken.
////    onSelect: (SearchResult.CustomMedia) -> Unit
//) {
//    Button(
//        onClick = {
//            // REFACTOR [23-12-20 1:44a.m.] -- hardcoded 0L id for new custom media...
//            // TODO [24-02-2 12:16a.m.] broken.
////            onSelect(SearchResult.CustomMedia(CustomMovie(
////                id=0L,
////                title=title
////            )))
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(64.dp)
//    ) {
//        Text("Add review for \"$title\"")
//    }
//}
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