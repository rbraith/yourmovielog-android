package com.rbraithwaite.yourmovielog.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.yourmovielog.core.data.SearchResult
import com.rbraithwaite.yourmovielog.core.repositories.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO [25-10-26 5:12p.m.] missing an Error state.
sealed interface SearchResultsUiState {
    /**
     * The UI search results state when there is no user input
     */
    data object NoInput : SearchResultsUiState

    /**
     * The UI search result state when the user has provided input and we are waiting for the results
     */
    data class Loading(
        val newCustomMediaTitle: String
    ) : SearchResultsUiState

    /**
     * The UI search result state with successfully found results.
     */
    data class Success(
        val newCustomMediaTitle: String,
        val searchResults: List<SearchResult>
    ) : SearchResultsUiState
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {
    private val _searchInputUiState = MutableStateFlow(initSearchInputUiState())
    val searchInputUiState = _searchInputUiState.asStateFlow()

    private val _searchResultsUiState = MutableStateFlow<SearchResultsUiState>(SearchResultsUiState.NoInput)
    val searchResultsUiState = _searchResultsUiState.asStateFlow()

    private fun initSearchInputUiState(): SearchInputUiState {
        return SearchInputUiState(
            createNewQuickSearchMultiInput(),
            ::onChangeSearchInputType,
            ::runSearch
        )
    }

    private fun createNewQuickSearchMultiInput(): QuickSearch.Multi {
        return QuickSearch.Multi(
            query = "",
            onChangeQuery = ::onChangeQuickSearchMultiQuery
        )
    }

    private fun onChangeQuickSearchMultiQuery(newQuery: String) {
        _searchInputUiState.update { uiState ->
            val quickSearchMulti = uiState.searchInput as QuickSearch.Multi
            uiState.copy(
                searchInput = quickSearchMulti.copy(query = newQuery)
            )
        }
    }

    private fun onChangeSearchInputType(searchInputType: SearchInputType) {
        // TODO [24-02-25 3:53p.m.] -- for now, just returning new search input instances.
        val newSearchInput = when (searchInputType) {
            QuickSearch::class -> createNewQuickSearchMultiInput()
            AdvancedSearch::class -> AdvancedSearch.Movie
            QuickSearch.Multi::class -> createNewQuickSearchMultiInput()
            QuickSearch.Movie::class -> QuickSearch.Movie
            QuickSearch.TvShow::class -> QuickSearch.TvShow
            QuickSearch.Person::class -> QuickSearch.Person
            AdvancedSearch.Movie::class -> AdvancedSearch.Movie
            AdvancedSearch.TvShow::class -> AdvancedSearch.TvShow
            else -> throw IllegalArgumentException("Invalid search input type: $searchInputType")
        }

        _searchInputUiState.update {
            it.copy(searchInput = newSearchInput)
        }
    }

    private fun runSearch() {
        val searchInput = _searchInputUiState.value.searchInput

        val searchFunc: suspend () -> List<SearchResult> = when (searchInput) {
            is QuickSearch.Multi -> {
                { mediaRepository.searchMulti(searchInput.query) }
            }
            else -> {
                TODO("Not implemented yet.")
            }
        }

        _searchResultsUiState.update {
            SearchResultsUiState.Loading("")
        }

        viewModelScope.launch {
            val searchResults = searchFunc()
            _searchResultsUiState.update {
                if (searchResults.isEmpty()) {
                    SearchResultsUiState.NoInput
                } else {
                    SearchResultsUiState.Success("", searchResults)
                }
            }
        }
    }
}
