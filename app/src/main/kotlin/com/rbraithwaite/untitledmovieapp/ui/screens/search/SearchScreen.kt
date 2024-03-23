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