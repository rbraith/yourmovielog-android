package com.rbraithwaite.untitledmovietracker.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.CustomMediaRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.asVarArg
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.core_data.aCustomMovie
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities.aCustomMovieEntity
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

class CustomMediaRepositoryImplTests {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val testDependencyManager = TestDependencyManager(testScope, testDispatcher)

    private val customMediaDao = testDependencyManager.customMediaDao

    private val customMediaRepository = CustomMediaRepositoryImpl(
        testScope,
        testDispatcher,
        customMediaDao
    )

    @Test
    fun addingCustomMovie() = testScope.runTest {
        // GIVEN a new custom movie
        // -------------------------------------------

        val expectedTitle = "addingCustomMovie"

        val customMovie = aCustomMovie()
            .asNewMovie()
            .withTitle(expectedTitle)
            .build()

        // WHEN that custom movie is added to the repository
        // -------------------------------------------

        customMediaRepository.addOrUpdateCustomMedia(customMovie)

        // THEN a correct entity is added to the custom media dao
        // -------------------------------------------

        argumentCaptor<CustomMovieEntity>().apply {
            verify(customMediaDao.mock).insertOrUpdateCustomMovies(capture())

            assertThat(allValues.size, willBe(1))

            val entity = allValues.first().asVarArg()[0]
            assertThat(entity.title, willBe(expectedTitle))
        }
    }

    @Test
    fun findMedia_test() = testScope.runTest {
        // GIVEN several saved custom media
        // -------------------------------------------

        val titleExpected = "what the"

        testDependencyManager.initializeDatabaseState {
            withCustomMovies(
                aCustomMovieEntity().withTitle(titleExpected),
                aCustomMovieEntity().withTitle("skip this title")
            )
        }

        // WHEN findMedia is run
        // -------------------------------------------

        val foundMedia = customMediaRepository.findMedia("what")

        // THEN the correct media is found
        // -------------------------------------------

        assertThat(foundMedia.size, willBe(1))

        val foundMovie = foundMedia.first() as CustomMedia.Movie
        assertThat(foundMovie.title, willBe(titleExpected))
    }
}