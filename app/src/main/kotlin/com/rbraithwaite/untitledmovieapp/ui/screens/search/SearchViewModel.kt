package com.rbraithwaite.untitledmovieapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// REFACTOR [23-10-22 1:00p.m.] -- rename to SearchInputUiState.
sealed interface SearchInput {
    data class Quick(
        val input: String = "",
        val updateInput: (String) -> Unit
    ): SearchInput
}

// REFACTOR [23-10-22 1:00p.m.] -- rename to SearchResultsUiState.
sealed interface SearchResults {
    data object NoInput: SearchResults
    data class Loading(
        val newCustomMediaTitle: String
    ): SearchResults

    // TODO [24-02-2 12:14a.m.] broken.
    data class Success(
        val newCustomMediaTitle: String,
//        val searchResults: List<SearchResult>
    ): SearchResults
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val customMediaRepository: CustomMediaRepository
): ViewModel() {
    private val _searchInput = MutableStateFlow<SearchInput>(
        SearchInput.Quick(updateInput = ::updateQuickSearchInput)
    )
    val searchInput = _searchInput.asStateFlow()

    private val _searchResults = MutableStateFlow<SearchResults>(SearchResults.NoInput)
    val searchResults = _searchResults.asStateFlow()

    init {
        // Derive search results from search input
        // REFACTOR [23-10-22 1:01p.m.] -- it would be nice to use stateIn instead maybe (so that
        //  the flow isn't always collecting), but I was having trouble testing that - however stateIn
        //  use gets exposed in tests annoyingly, so maybe not.
        viewModelScope.launch {
            // TODO [23-10-22 11:44a.m.] -- add debounce probably.
            // TODO [23-10-30 12:39a.m.] -- should be collectLatest?
            _searchInput
                .onEach { newInput ->
                    // When search input changes, run a side effect to set Loading result
                    // Loading remains while the debounce is waiting
                    val newResults = when (newInput) {
                        is SearchInput.Quick -> {
                            if (newInput.input.isEmpty()) {
                                SearchResults.NoInput
                            } else {
                                SearchResults.Loading(newInput.input)
                            }
                        }
                    }
                    _searchResults.update { newResults }
                }
                // Let's not go crazy with api calls...
                // SMELL [23-11-12 12:15a.m.] -- this is sorta weird, maybe the wrong place for this
                //  debounce - ideally should move to repo so viewmodel doesn't know about the api
                //  call.
                .debounce(5000)
                .collect { newInput ->
                    val newResults: SearchResults = when (newInput) {
                        is SearchInput.Quick -> {
                            if (newInput.input.isEmpty()) {
                                SearchResults.NoInput
                            } else {
                                // TODO [23-10-22 11:57a.m.] -- this findMedia arg will need to eventually be some kind of
                                //  SearchCriteria class instead of just a string - look at old task app search filter
                                //  logic.
                                //  I'll need to compose the quick-search criteria here (setting user input to the
                                //  title, director, cast, etc).
                                val media = customMediaRepository.findMedia(newInput.input)

                                // TODO [24-02-2 12:15a.m.] broken.
                                SearchResults.Success(
                                    newInput.input,
//                                    media
                                )
                            }
                        }
                    }
                    _searchResults.update { newResults }
                }
        }
    }

    private fun updateQuickSearchInput(newInput: String) {
        _searchInput.update {
            when (it) {
                is SearchInput.Quick -> {
                    it.copy(input = newInput)
                }
            }
        }
    }
}