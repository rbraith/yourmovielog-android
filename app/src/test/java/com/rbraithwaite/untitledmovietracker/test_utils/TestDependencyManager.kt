package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.test_data_utils.valueOf
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.CustomMovieEntityBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.ReviewEntityBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.CustomMovieEntityIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeCustomMediaRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeReviewRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeCustomMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeDatabase
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeTmdbDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeReviewDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeTmdbApiV3
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.LongIdSelector
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.ReviewEntityIdSelector
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

    interface TestStateInitializer {
        // TODO [24-02-2 12:27a.m.] broken.
//        // TODO [24-01-19 11:27p.m.] -- this doesn't allow for custom ids, since adding through
//        //  the repo ignores the given id (also see impl of FakeMediaDao, the FakeDatabase sets the id).
//        /**
//         * @param customMedia The custom media to initialize the repo with
//         */
//        suspend fun withCustomMedia(vararg customMedia: CustomMediaBuilder)
//
//        suspend fun withTmdbLiteMovies(vararg movies: TmdbLiteMovieBuilder)
//
//        /**
//         * @param reviews These are pairs of (review, tmdb movie id)
//         */
//        suspend fun withMediaReviewsForTmdbMovie(vararg reviews: Pair<MediaReviewBuilder, Long>)
//
//        /**
//         * @param mediaReviews These are pairs of (review, custom media id)
//         */
//        suspend fun withMediaReviewsForCustomMedia(vararg mediaReviews: Pair<MediaReviewBuilder, Long>)

        suspend fun withBackendSearchResults(
            movies: List<SearchMultiResult.Movie>,
            tvShows: List<SearchMultiResult.TvShow>,
            people: List<SearchMultiResult.Person>
        )
    }

    private inner class TestBackendStateInitializerImpl: TestBackendStateInitializer {
        // TODO [24-02-2 12:28a.m.] broken.
//        override suspend fun withCustomMedia(vararg customMedia: CustomMediaBuilder) {
//            customMedia.forEach {
//                this@TestDependencyManager.mediaRepository.withMockEnabled(false) {
                // TODO [24-02-2 12:28a.m.] broken.
////                    addOrUpdateCustomMovies(valueOf(it))
//                }
//            }
//        }
//
//        override suspend fun withTmdbLiteMovies(vararg movies: TmdbLiteMovieBuilder) {
//            movies.forEach { movie ->
//                this@TestDependencyManager.mediaRepository.addOrUpdateTmdbLite(movie.build())
//            }
//        }
//
//        override suspend fun withMediaReviewsForTmdbMovie(vararg reviews: Pair<MediaReviewBuilder, Long>) {
//            reviews.forEach { (mediaReview, tmdbMovieId) ->
//                this@TestDependencyManager.mediaRepository.addTmdbMovieReview(
//                    tmdbMovieId,
//                    mediaReview.build()
//                )
//            }
//        }
//
//        override suspend fun withMediaReviewsForCustomMedia(vararg mediaReviews: Pair<MediaReviewBuilder, Long>) {
//            mediaReviews.forEach { (mediaReview, customMediaId) ->
//                this@TestDependencyManager.reviewRepository.addReviewForCustomMovie(
//                    mediaReview.build(),
//                    customMediaId
//                )
//            }
//        }

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