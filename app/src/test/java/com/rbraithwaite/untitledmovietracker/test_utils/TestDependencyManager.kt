package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.test_data_utils.valueOf
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.CustomMediaBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeMediaRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeDatabase
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeTmdbApiV3
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Responsible for connecting test dependencies and initializing those dependencies.
 */
class TestDependencyManager(
    private val externalScope: CoroutineScope,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val localDatabase: FakeDatabase by lazy {
        FakeDatabase(listOf(
            CustomMediaEntity::class,
            MediaReviewEntity::class
        ))
    }

    private val backend: FakeDatabase by lazy {
        FakeDatabase(listOf(
            SearchMultiResult.Movie::class,
            SearchMultiResult.TvShow::class,
            SearchMultiResult.Person::class
        ))
    }

    private val mediaDao: FakeMediaDao by lazy {
        FakeMediaDao(localDatabase)
    }

    private val tmdbApiV3: FakeTmdbApiV3 by lazy {
        FakeTmdbApiV3(backend)
    }

    val mediaRepository: DelegateFakeMediaRepository by lazy {
        DelegateFakeMediaRepository(
            externalScope,
            coroutineDispatcher,
            mediaDao,
            tmdbApiV3
        )
    }

    // *********************************************************
    // Test State Initialization
    // *********************************************************
    //region Test State Initialization

    interface TestStateInitializer {
        /**
         * @param customMedia The custom media to initialize the repo with
         */
        suspend fun withCustomMedia(vararg customMedia: CustomMediaBuilder)

        suspend fun withBackendSearchResults(
            movies: List<SearchMultiResult.Movie>,
            tvShows: List<SearchMultiResult.TvShow>,
            people: List<SearchMultiResult.Person>
        )
    }

    private inner class TestStateInitializerImpl: TestStateInitializer {
        override suspend fun withCustomMedia(vararg customMedia: CustomMediaBuilder) {
            customMedia.forEach {
                this@TestDependencyManager.mediaRepository.withMockEnabled(false) {
                    addNewCustomMedia(valueOf(it))
                }
            }
        }

        override suspend fun withBackendSearchResults(
            movies: List<SearchMultiResult.Movie>,
            tvShows: List<SearchMultiResult.TvShow>,
            people: List<SearchMultiResult.Person>
        ) {
            val depManager = this@TestDependencyManager

            for (movie in movies) {
                depManager.backend.insert(movie) { copy(id = it.toInt()) }
            }
            for (tvShow in tvShows) {
                depManager.backend.insert(tvShow) { copy(id = it.toInt()) }
            }
            for (person in people) {
                depManager.backend.insert(person) { copy(id = it.toInt()) }
            }
        }
    }

    suspend fun initializeTestState(initialize: suspend TestStateInitializer.() -> Unit) {
        TestStateInitializerImpl().initialize()
    }

    //endregion
}