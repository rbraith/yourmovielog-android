package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.CustomMovieEntityBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.ReviewEntityBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.CustomMovieEntityIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories.DelegateFakeCustomMediaRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories.DelegateFakeReviewRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeCustomMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeDatabase
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeTmdbDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeReviewDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.network.FakeTmdbApiV3
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.LongIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.ReviewEntityIdSelector
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Responsible for connecting test dependencies and initializing those dependencies. For example,
 * ensuring all fake DAOs share the same database.
 *
 * Dependencies are instantiated lazily so that you can take only what you need.
 */
class TestDependencyManager(
    private val externalScope: CoroutineScope,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val localDatabase: FakeDatabase by lazy {
        FakeDatabase(listOf(
            CustomMovieEntity::class,
            ReviewEntity::class,
            TmdbLiteMovieEntity::class,
            TmdbLiteMovieGenreJunction::class
        ))
    }

    private val backend: FakeDatabase by lazy {
        FakeDatabase(listOf(
            SearchMultiResult.Movie::class,
            SearchMultiResult.TvShow::class,
            SearchMultiResult.Person::class
        ))
    }

    val tmdbDao: FakeTmdbDao by lazy {
        FakeTmdbDao(localDatabase)
    }

    val reviewDao: FakeReviewDao by lazy {
        FakeReviewDao(localDatabase)
    }

    val tmdbApiV3: FakeTmdbApiV3 by lazy {
        FakeTmdbApiV3(backend)
    }

    val customMediaDao: FakeCustomMediaDao by lazy {
        FakeCustomMediaDao(localDatabase)
    }

    val reviewRepository: DelegateFakeReviewRepository by lazy {
        DelegateFakeReviewRepository(
            reviewDao,
            tmdbDao,
            customMediaDao,
            externalScope,
            coroutineDispatcher
        )
    }

    val mediaRepository: DelegateFakeCustomMediaRepository by lazy {
        DelegateFakeCustomMediaRepository(
            externalScope,
            coroutineDispatcher,
            customMediaDao
        )
    }

    // *********************************************************
    // Test State Initialization
    // *********************************************************
    //region Test State Initialization

    interface TestDatabaseStateInitializer {
        suspend fun withCustomMovies(vararg customMovies: CustomMovieEntityBuilder)
        suspend fun withReviews(vararg reviews: ReviewEntityBuilder)
    }

    interface TestBackendStateInitializer {
        // SMELL [24-02-6 12:12a.m.] -- this isn't good, it reveals information about which
        //  api method is used. It'd be better to properly populate the backend as a database,
        //  using full tmdb media detail objs.
        suspend fun withSearchMultiResults(
            movies: List<SearchMultiResult.Movie> = emptyList(),
            tvShows: List<SearchMultiResult.TvShow> = emptyList(),
            people: List<SearchMultiResult.Person> = emptyList()
        )
    }

    private inner class TestDatabaseStateInitializerImpl: TestDatabaseStateInitializer {
        override suspend fun withCustomMovies(vararg customMovies: CustomMovieEntityBuilder) {
            customMovies.map { it.build() }.forEach {
                this@TestDependencyManager.localDatabase.insert(it, CustomMovieEntityIdSelector())
            }
        }

        override suspend fun withReviews(vararg reviews: ReviewEntityBuilder) {
            reviews.map { it.build() }.forEach {
                this@TestDependencyManager.localDatabase.insert(it, ReviewEntityIdSelector())
            }
        }
    }

    private inner class TestBackendStateInitializerImpl: TestBackendStateInitializer {
        override suspend fun withSearchMultiResults(
            movies: List<SearchMultiResult.Movie>,
            tvShows: List<SearchMultiResult.TvShow>,
            people: List<SearchMultiResult.Person>
        ) {
            val depManager = this@TestDependencyManager

            val movieIdSelector = SearchMultiResultMovieIdSelector()
            for (movie in movies) {
                depManager.backend.insert(
                    movie,
                    movieIdSelector
                )
            }

            // TODO [24-02-6 12:17a.m.] -- eh let's worry about these later.
//            for (tvShow in tvShows) {
//                depManager.backend.insert(tvShow)
//            }
//            for (person in people) {
//                depManager.backend.insert(person)
//            }
        }
    }

    suspend fun initializeDatabaseState(initBlock: suspend TestDatabaseStateInitializer.() -> Unit) {
        TestDatabaseStateInitializerImpl().initBlock()
    }

    suspend fun initializeBackendState(initialize: suspend TestBackendStateInitializer.() -> Unit) {
        TestBackendStateInitializerImpl().initialize()
    }

    //endregion
}

private class SearchMultiResultMovieIdSelector: LongIdSelector<SearchMultiResult.Movie>() {
    override fun getId(entity: SearchMultiResult.Movie): Long {
        return entity.id
    }

    override fun updateId(entity: SearchMultiResult.Movie, newId: Long): SearchMultiResult.Movie {
        return entity.copy(id = newId)
    }
}