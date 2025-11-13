package com.rbraithwaite.untitledmovietracker.ui.screen_search

import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.data.TmdbData
import com.rbraithwaite.untitledmovieapp.ui.screens.search.AdvancedSearch
import com.rbraithwaite.untitledmovieapp.ui.screens.search.QuickSearch
import com.rbraithwaite.untitledmovieapp.ui.screens.search.SearchResultsUiState
import com.rbraithwaite.untitledmovieapp.ui.screens.search.SearchViewModel
import com.rbraithwaite.untitledmovietracker.test_utils.rules.MainDispatcherRule
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models.aMovie
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
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
import kotlin.math.exp

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

    @Test
    fun quickSearchMultiTest() = runTest {
        // GIVEN some TMDB Media with a particular title
        // ------------------------------------------
        val expectedTitle = "expected"

        testDependencyManager.initializeBackendState {
            withMovies(
                aMovie().withTitle(expectedTitle),
                aMovie().withTitle("invalid")
            )
        }

        val searchResults = viewModel.searchResultsUiState
        assertThat("", searchResults.value is SearchResultsUiState.NoInput)

        // WHEN a "quick search multi" is run with some input matching that media title
        // -----------------------------------------
        val searchInputUiState = viewModel.searchInputUiState.value
        val quickSearch = searchInputUiState.searchInput as QuickSearch.Multi

        quickSearch.onChangeQuery(expectedTitle)
        searchInputUiState.runSearch()

        // THEN the results are updated and show that media
        // ------------------------------------------
        // TODO [25-11-6 1:37a.m.] possible dispatcher bug here - how do I wait for the coroutine in SearchViewModel.runSearch()?
        //     wouldn't I need a StandardTestDispatcher? but main is replaced with Unconfined
        val successResult = searchResults.value as SearchResultsUiState.Success

        with (successResult.searchResults) {
            assertThat(size, willBe(1))

            val movie = (get(0) as SearchResult.Tmdb).value as TmdbData.Movie
            assertThat(movie.title, willBe(expectedTitle))
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