package com.rbraithwaite.untitledmovietracker.ui.screen_search

import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchInput
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.willBeEqualTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SearchViewModelTests {

    private val viewModel = SearchViewModel()

    @Test
    fun initialSearchInputStateIsQuick() {
        val searchInput = viewModel.searchInput
        assertThat("", searchInput.value is SearchInput.Quick)
    }

    @Test
    fun quickSearchInputUpdatesProperly() {
        val searchInput = viewModel.searchInput
        var quickInput = searchInput.value as SearchInput.Quick
        assertThat(quickInput.input, willBeEqualTo(""))

        val expectedUpdatedInput = "updated input"
        quickInput.updateInput(expectedUpdatedInput)

        quickInput = searchInput.value as SearchInput.Quick
        assertThat(quickInput.input, willBeEqualTo(expectedUpdatedInput))
    }
}