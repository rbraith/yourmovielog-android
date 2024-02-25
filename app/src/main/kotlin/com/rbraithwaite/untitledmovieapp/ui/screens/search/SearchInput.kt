package com.rbraithwaite.untitledmovieapp.ui.screens.search

import kotlin.reflect.KClass

sealed interface SearchInput

sealed interface QuickSearch: SearchInput  {
    data class Multi(
        val query: String,
        val onChangeQuery: (String) -> Unit
    ): QuickSearch
    data object Movie: QuickSearch
    data object TvShow: QuickSearch
    data object Person: QuickSearch
}

sealed interface AdvancedSearch: SearchInput {
    data object Movie: AdvancedSearch
    data object TvShow: AdvancedSearch
}

typealias SearchInputType = KClass<out SearchInput>

data class SearchInputUiState(
    val searchInput: SearchInput,
    val onChangeSearchInputType: (SearchInputType) -> Unit
)