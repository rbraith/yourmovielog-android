package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.test_data_utils.valueOf
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeCustomMediaRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeReviewRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeCustomMediaDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeDatabase
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeTmdbDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeReviewDao
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeTmdbApiV3
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

    val mediaDao: FakeTmdbDao by lazy {
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
            mediaDao,
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

    private inner class TestStateInitializerImpl: TestStateInitializer {
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

        override suspend fun withBackendSearchResults(
            movies: List<SearchMultiResult.Movie>,
            tvShows: List<SearchMultiResult.TvShow>,
            people: List<SearchMultiResult.Person>
        ) {
            val depManager = this@TestDependencyManager

            TODO("broken")
//            for (movie in movies) {
//                depManager.backend.insert(movie) { copy(id = it) }
//            }
//            for (tvShow in tvShows) {
//                depManager.backend.insert(tvShow) { copy(id = it) }
//            }
//            for (person in people) {
//                depManager.backend.insert(person) { copy(id = it) }
//            }
        }
    }

    suspend fun initializeTestState(initialize: suspend TestStateInitializer.() -> Unit) {
        TestStateInitializerImpl().initialize()
    }

    //endregion
}