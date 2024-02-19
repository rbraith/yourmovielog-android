package com.rbraithwaite.untitledmovietracker.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.TmdbRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.core_data.aTmdbLiteMovie
import com.rbraithwaite.untitledmovietracker.test_utils.asVarArg
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.network_models.aMovie
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import com.rbraithwaite.untitledmovietracker.test_utils.willBeEqualTo
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class TmdbRepositoryImplTests {
    // REFACTOR [24-02-5 11:36p.m.] -- do something better with these duplicated coroutine &
    //  testDependencyManager fields
    //  maybe a test rule? or a better utility class idk.
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val testDependencyManager = TestDependencyManager(testScope, testDispatcher)

    private val tmdbDao = testDependencyManager.tmdbDao

    private val tmdbRepository = TmdbRepositoryImpl(
        tmdbDao,
        testDependencyManager.tmdbApiV3,
        testScope,
        testDispatcher
    )

    @Test
    fun upsertTmdbLite_addsMovies() = testScope.runTest {
        // GIVEN several TmdbLite.Movies
        // -------------------------------------------

        val movie1 = aTmdbLiteMovie()
            .withId(123L)
            .withGenreIds(listOf(4, 5, 6))
            .build()

        val movie2 = aTmdbLiteMovie()
            .withId(321L)
            .withGenreIds(listOf(7, 8, 9))
            .build()

        // WHEN you add those movies to the repo
        // -------------------------------------------

        tmdbRepository.upsertTmdbLite(
            movie1,
            movie2
        )

        // THEN the repo calls the dao with the expected methods
        // -------------------------------------------

        // verify upsertTmdbLiteMovies()
        argumentCaptor<TmdbLiteMovieEntity>().apply {
            verify(tmdbDao.mock).upsertTmdbLiteMovies(capture())

            val varArg = allValues.first().asVarArg()

            assertThat(varArg.size, willBe(2))

            assertThat(varArg[0].id, willBe(movie1.id))
            assertThat(varArg[1].id, willBe(movie2.id))
        }

        // verify upsertGenreIdsForMovie()
        val movieIdCaptor = argumentCaptor<Long>()
        val genreIdsCaptor = argumentCaptor<List<Int>>()

        verify(tmdbDao.mock, times(2)).upsertGenreIdsForMovie(
            movieIdCaptor.capture(),
            genreIdsCaptor.capture()
        )

        val movieList = listOf(movie1, movie2)
        for (movie in movieList) {
            assert(movieIdCaptor.allValues.contains(movie.id))
            assert(genreIdsCaptor.allValues.contains(movie.genreIds))
        }
    }

    @Test
    fun findTmdbLite_findsMatchingMovie() = testScope.runTest {
        // GIVEN a backend with some movies
        // -------------------------------------------

        val expectedMovieId = 987L

        val movieExpected = aMovie()
            .withTitle("hello world")
            .withId(expectedMovieId)
            .build()

        val movieShouldNotFind = aMovie()
            .withTitle("shouldn't be found")
            .withId(123L)
            .build()

        testDependencyManager.initializeBackendState {
            withSearchMultiResults(
                movies = listOf(movieExpected, movieShouldNotFind)
            )
        }

        // WHEN you search the repo
        // -------------------------------------------

        val searchKey = "hello"

        val foundMedia = tmdbRepository.findTmdbLite(searchKey)

        // THEN matching movies are returned
        // -------------------------------------------

        assertThat(foundMedia.size, willBe(1))

        val foundMovie = foundMedia.first() as TmdbLite.Movie
        assertThat(foundMovie.id, willBeEqualTo(expectedMovieId))
    }
}