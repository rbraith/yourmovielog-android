package com.rbraithwaite.untitledmovietracker.ui.screen_search

import com.rbraithwaite.untitledmovieapp.ui.screens.search.AdvancedSearch
import com.rbraithwaite.untitledmovieapp.ui.screens.search.QuickSearch
import com.rbraithwaite.untitledmovieapp.ui.screens.search.SearchInput
import com.rbraithwaite.untitledmovieapp.ui.screens.search.SearchViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
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
    private val viewModel by lazy { SearchViewModel(/*fakeMediaRepository*/) }

    @Test
    fun initialSearchInputStateIsQuickSearchMulti() {
        val searchInputUiState = viewModel.searchInputUiState
        assertThat("", searchInputUiState.value.searchInput is QuickSearch.Multi)
    }

    @Test
    fun initialAdvancedSearchInputStateIsMovie() {
        val searchInputUiState = viewModel.searchInputUiState
        searchInputUiState.value.onChangeSearchInputType(AdvancedSearch::class)
        assert(searchInputUiState.value.searchInput is AdvancedSearch.Movie)
    }

    @Test
    fun onChangeSearchInputType_updatesState() {
        // GIVEN the search-input UI state
        // -------------------------------------------

        val searchInputUiState = viewModel.searchInputUiState

        // WHEN you change the selected type
        // -------------------------------------------

        searchInputUiState.value.onChangeSearchInputType(AdvancedSearch.TvShow::class)

        // THEN that change is reflected in the UI state
        // -------------------------------------------

        assert(searchInputUiState.value.searchInput is AdvancedSearch.TvShow)
    }

    @Test
    fun initialSearchResultsIsNoInput() {
        // TODO [24-02-25 4:46p.m.] -- broken.
//        val searchResults = viewModel.searchResults
//        assertThat(searchResults.value is SearchResults.NoInput, willBe(true))
    }

    // BUG [24-01-13 8:02p.m.] -- I think this should be using mainDispatcherRule.testScope.runTest()
    @Test
    fun changingQuickSearchInputUpdatesResults() = runTest {
        // TODO [24-02-2 12:09a.m.] broken.
//        // GIVEN a repository with some Media
//        // -------------------------------------------
//        val expectedTitle = "expected"
//
//        testDependencyManager.initializeTestState {
//            withCustomMedia(
//                aCustomMedia().withTitle(expectedTitle),
//                // REFACTOR [23-11-20 10:09p.m.] -- hardcoded title.
//                aCustomMedia().withTitle("nope")
//            )
//            // REFACTOR [23-11-19 4:43p.m.] -- maybe extract tmdb search to a separate test?
//            //  maybe parameterize this test?.
//            withBackendSearchResults(
//                listValuesOf(
//                    aSearchMultiResultMovie().withTitle(expectedTitle),
//                    aSearchMultiResultMovie().withTitle("nope")
//                ),
//                listValuesOf(
//                    aSearchMultiResultTvShow().withName(expectedTitle),
//                    aSearchMultiResultTvShow().withName("nope")
//                ),
//                listValuesOf(
//                    aSearchMultiResultPerson().withName(expectedTitle),
//                    aSearchMultiResultPerson().withName("nope")
//                )
//            )
//        }
//
//        val searchResults = viewModel.searchResults
//        assertThat("", searchResults.value is SearchResults.NoInput)
//
//        // WHEN quick search is updated to some input matching a media title
//        // -------------------------------------------
//        val searchInput = viewModel.searchInput
//        val quickSearch = searchInput.value as SearchInput.Quick
//
//        val userInput = "expect"
//        quickSearch.updateInput(userInput)
//
//        // REFACTOR [23-11-20 10:09p.m.] -- hardcoded delay - magic knowledge of the debounce time.
//        delay(6000)
//
//        // THEN that media appears in the results
//        // -------------------------------------------
//        val success = searchResults.value as SearchResults.Success
//
//        assertThat(success.newCustomMediaTitle, willBe(userInput))
//
//        with (success.searchResults) {
//            assertThat(size, willBe(4))
//
//            // custom media results are first
//            val foundCustomMedia = get(0) as SearchResult.CustomMedia
//            assertThat(foundCustomMedia.data.title, willBe(expectedTitle))
//
//            // remaining are tmdb results, unordered
//            // TODO [23-11-20 11:03p.m.] -- weird hamcrest syntax here, for some reason notNull()
//            //  wasn't working for me.
//            assertThat(
//                find { (it as? SearchResult.TmdbMovie)?.data?.title == expectedTitle } != null,
//                willBe(true)
//            )
//
//            assertThat(
//                find { (it as? SearchResult.TmdbTvShow)?.data?.name == expectedTitle } != null,
//                willBe(true)
//            )
//
//            assertThat(
//                find { (it as? SearchResult.TmdbPerson)?.data?.name == expectedTitle } != null,
//                willBe(true)
//            )
//        }
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
        // TODO [24-02-25 4:47p.m.] -- broken.
//        val searchInput = viewModel.searchInputUiState
//        var quickInput = searchInput.value as SearchInput.Quick
//        assertThat(quickInput.input, willBeEqualTo(""))
//
//        val expectedUpdatedInput = "updated input"
//        quickInput.updateInput(expectedUpdatedInput)
//
//        quickInput = searchInput.value as SearchInput.Quick
//        assertThat(quickInput.input, willBeEqualTo(expectedUpdatedInput))
    }
}