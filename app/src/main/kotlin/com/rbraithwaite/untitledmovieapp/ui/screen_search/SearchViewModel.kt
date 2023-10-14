package com.rbraithwaite.untitledmovieapp.ui.screen_search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface SearchInput {
    data class Quick(
        val input: String = "",
        val updateInput: (String) -> Unit
    ): SearchInput
}

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    private val _searchInput = MutableStateFlow<SearchInput>(
        SearchInput.Quick(updateInput = ::updateQuickSearchInput)
    )
    val searchInput = _searchInput.asStateFlow()

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