package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.network.models.Movie
import com.rbraithwaite.untitledmovieapp.data.network.models.Person
import com.rbraithwaite.untitledmovieapp.data.network.models.TvShow
import com.rbraithwaite.untitledmovieapp.data.repositories.MediaRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.CustomMovieEntityBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.ReviewEntityBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models.MovieBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models.TvShowBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.CustomMovieEntityIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories.DelegateFakeReviewRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeCustomMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeDatabase
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeTmdbDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.FakeReviewDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.network.FakeTmdbApiV3
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.LongIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.database.ReviewEntityIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.repositories.MockDelegateMediaRepository
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
            MediaMovieEntity::class,
            MediaReviewEntity::class,
            CustomMovieEntity::class,
            ReviewEntity::class,
            TmdbLiteMovieEntity::class,
            TmdbLiteMovieGenreJunction::class
        ))
    }

    private val backend: FakeDatabase by lazy {
        FakeDatabase(listOf(
            Movie::class,
            TvShow::class,
            Person::class
        ))
    }

    val mediaDao: FakeMediaDao by lazy {
        FakeMediaDao(localDatabase)
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

    val reviewRepository: DelegateFakeReviewRepository by lazy {
        DelegateFakeReviewRepository(
            reviewDao,
            tmdbDao,
            externalScope,
            coroutineDispatcher
        )
    }

    val mediaRepository: MockDelegateMediaRepository by lazy {
        MockDelegateMediaRepository(MediaRepositoryImpl(
            coroutineDispatcher,
            externalScope,
            tmdbApiV3,
            mediaDao
        ))
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
        suspend fun withMovies(vararg movies: MovieBuilder)
        suspend fun withTvShows(vararg tvShows: TvShowBuilder)
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
        override suspend fun withMovies(vararg movies: MovieBuilder) {
            val depManager = this@TestDependencyManager

            val movieIdSelector = MovieIdSelector()
            for (movie in movies) {
                depManager.backend.insert(
                    movie.build(),
                    movieIdSelector
                )
            }
        }

        override suspend fun withTvShows(vararg tvShows: TvShowBuilder) {
            val depManager = this@TestDependencyManager

            val idSelector = TvShowIdSelector()
            for (tvShow in tvShows) {
                depManager.backend.insert(tvShow.build(), idSelector)
            }
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

private class MovieIdSelector: LongIdSelector<Movie>() {
    override fun getId(entity: Movie): Long {
        return entity.id
    }

    override fun updateId(
        entity: Movie,
        newId: Long
    ): Movie {
        return entity.copy(id = newId)
    }
}

private class TvShowIdSelector: LongIdSelector<TvShow>() {
    override fun getId(entity: TvShow): Long {
        return entity.id
    }

    override fun updateId(
        entity: TvShow,
        newId: Long
    ): TvShow {
        return entity.copy(id = newId)
    }
}