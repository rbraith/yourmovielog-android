package com.rbraithwaite.yourmovielog.ui.screens.search

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlin.reflect.KClass

private enum class FirstRowTab(val text: String) {
    QUICK("Quick"),
    ADVANCED("Advanced")
}

private enum class QuickSearchModeTab(val text: String) {
    MULTI("All"),
    MOVIE("Movies"),
    TV_SHOW("Tv"),
    PERSON("People")
}

private enum class AdvancedSearchModeTab(val text: String) {
    MOVIE("Movies"),
    TV_SHOW("Tv")
}

private fun SearchInput.deriveFirstRowTab(): FirstRowTab = when (this) {
    is QuickSearch -> FirstRowTab.QUICK
    is AdvancedSearch -> FirstRowTab.ADVANCED
}

private fun SearchInput.deriveFirstRowTabIndex(): Int {
    return FirstRowTab.entries.indexOf(deriveFirstRowTab())
}

private fun FirstRowTab.deriveSelection(): SearchInputType = when (this) {
    FirstRowTab.QUICK -> QuickSearch::class
    FirstRowTab.ADVANCED -> AdvancedSearch::class
}

private fun QuickSearch.deriveTab(): QuickSearchModeTab = when (this) {
    is QuickSearch.Multi -> QuickSearchModeTab.MULTI
    is QuickSearch.Movie -> QuickSearchModeTab.MOVIE
    is QuickSearch.TvShow -> QuickSearchModeTab.TV_SHOW
    is QuickSearch.Person -> QuickSearchModeTab.PERSON
}

private fun QuickSearch.deriveTabIndex(): Int {
    return QuickSearchModeTab.entries.indexOf(deriveTab())
}

private fun QuickSearchModeTab.deriveSelection(): SearchInputType = when (this) {
    QuickSearchModeTab.MULTI -> QuickSearch.Multi::class
    QuickSearchModeTab.MOVIE -> QuickSearch.Movie::class
    QuickSearchModeTab.TV_SHOW -> QuickSearch.TvShow::class
    QuickSearchModeTab.PERSON -> QuickSearch.Person::class
}

private fun AdvancedSearch.deriveTab(): AdvancedSearchModeTab = when (this) {
    is AdvancedSearch.Movie -> AdvancedSearchModeTab.MOVIE
    is AdvancedSearch.TvShow -> AdvancedSearchModeTab.TV_SHOW
}

private fun AdvancedSearch.deriveTabIndex(): Int {
    return AdvancedSearchModeTab.entries.indexOf(deriveTab())
}

private fun AdvancedSearchModeTab.deriveSelection(): SearchInputType = when (this) {
    AdvancedSearchModeTab.MOVIE -> AdvancedSearch.Movie::class
    AdvancedSearchModeTab.TV_SHOW -> AdvancedSearch.TvShow::class
}

@Composable
fun SearchInputModeSelector(
    searchInput: SearchInput,
    onSearchInputModeSelected: (KClass<out SearchInput>) -> Unit
) {
    TabRow(
        selectedTabIndex = searchInput.deriveFirstRowTabIndex()
    ) {
        FirstRowTab.entries.forEach { firstRowTab ->
            Tab(
                text = { Text(firstRowTab.text) },
                selected = true,
                onClick = {
                    onSearchInputModeSelected(firstRowTab.deriveSelection())
                }
            )
        }
    }

    if (searchInput is QuickSearch) {
        TabRow(
            selectedTabIndex = searchInput.deriveTabIndex()
        ) {
            QuickSearchModeTab.entries.forEach { tab ->
                Tab(
                    text = { Text(tab.text) },
                    selected = true,
                    onClick = {
                        onSearchInputModeSelected(tab.deriveSelection())
                    }
                )
            }
        }
    } else if (searchInput is AdvancedSearch) {
        TabRow(
            selectedTabIndex = searchInput.deriveTabIndex()
        ) {
            AdvancedSearchModeTab.entries.forEach { tab ->
                Tab(
                    text = { Text(tab.text) },
                    selected = true,
                    onClick = {
                        onSearchInputModeSelected(tab.deriveSelection())
                    }
                )
            }
        }
    }
}
