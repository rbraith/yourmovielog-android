package com.rbraithwaite.untitledmovietracker.ui.screen_search

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchInput
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchResults
import com.rbraithwaite.untitledmovieapp.ui.screen_search.SearchViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.aCustomMedia
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeMediaRepository
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import com.rbraithwaite.untitledmovietracker.test_utils.willBeEqualTo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class SearchViewModelTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testDependencyManager = TestDependencyManager(
        mainDispatcherRule.testScope,
        mainDispatcherRule.testDispatcher
    )
    private val fakeMediaRepository = testDependencyManager.mediaRepository

    // lazy seems to be needed for MainDispatcherRule to work?? (can't init viewModel right here,
    // needs to be when running the test)
    private val viewModel by lazy { SearchViewModel(fakeMediaRepository) }

    @Test
    fun initialSearchInputStateIsQuick() {
        val searchInput = viewModel.searchInput
        assertThat("", searchInput.value is SearchInput.Quick)
    }

    @Test
    fun initialSearchResultsIsNoInput() {
        val searchResults = viewModel.searchResults
        assertThat(searchResults.value is SearchResults.NoInput, willBe(true))
    }

    @Test
    fun changingQuickSearchInputUpdatesResults() = runTest {
        // GIVEN a repository with some Media
        // -------------------------------------------
        val expectedTitle = "expected"

        testDependencyManager.initializeTestState {
            withCustomMedia(
                aCustomMedia().withTitle(expectedTitle),
                aCustomMedia().withTitle("nope")
            )
        }

        val searchResults = viewModel.searchResults
        assertThat("", searchResults.value is SearchResults.NoInput)

        // WHEN quick search is updated to some input matching a media title
        // -------------------------------------------
        val searchInput = viewModel.searchInput
        val quickSearch = searchInput.value as SearchInput.Quick

        val userInput = "expect"
        quickSearch.updateInput(userInput)

        // THEN that media appears in the results
        // -------------------------------------------
        val success = searchResults.value as SearchResults.Success

        assertThat(success.newCustomMediaTitle, willBe(userInput))

        with (success.mediaResults) {
            assertThat(size, willBe(1))

            val foundMedia = get(0) as CustomMedia
            assertThat(foundMedia.title, willBe(expectedTitle))
        }
    }

    // REFACTOR [23-10-29 4:38p.m.] -- keep this, but move it.
    // https://developer.android.com/kotlin/flow/test#statein
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun <T> TestScope.fixForStateIn(stateFlow: StateFlow<T>) {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            stateFlow.collect()
        }
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