package com.rbraithwaite.yourmovielog.data.network.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.rbraithwaite.yourmovielog.data.database.AppDatabase
import com.rbraithwaite.yourmovielog.data.database.dao.MediaDao
import com.rbraithwaite.yourmovielog.data.repositories.MediaRepositoryImpl
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aTvEpisode
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aTvSeason
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.aTvShow
import com.rbraithwaite.yourmovielog.test_utils.fakes.network.FakeTmdbApiV3
import com.rbraithwaite.yourmovielog.test_utils.rules.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
class MediaRepositoryImplTests {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var mediaDao: MediaDao

    private val tmdbApiV3 = FakeTmdbApiV3()

    private lateinit var mediaRepository: MediaRepositoryImpl

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        mediaDao = database.mediaDao()

        mediaRepository = MediaRepositoryImpl(
            mainDispatcherRule.testDispatcher,
            mainDispatcherRule.testScope,
            tmdbApiV3,
            mediaDao
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun addMediaTvShow() = runTest {
        // GIVEN a tv show
        // ------------------------------------------
        val tvShow = aTvShow().build()

        // WHEN it is added to the media repository
        // ------------------------------------------
        mediaRepository.addMedia(tvShow)

        // THEN the tv show is persisted in the database
        // ------------------------------------------
        val persistedTvShow = mediaDao.getTvShowById(tvShow.uuid.toString())
        assertThat("persisted tv show is null", persistedTvShow != null)
        assertThat("uuids don't match", UUID.fromString(persistedTvShow!!.uuid) == tvShow.uuid)
    }

    @Test
    fun addMediaTvSeason() = runTest {
        // GIVEN a tv season
        // ------------------------------------------
        val tvSeason = aTvSeason().build()

        // WHEN it is added to the media repository
        // ------------------------------------------
        mediaRepository.addMedia(tvSeason)

        // THEN the tv season is persisted in the database
        // ------------------------------------------
        val persistedTvSeason = mediaDao.getTvSeasonById(tvSeason.uuid.toString())
        assertThat("persisted tv season is null", persistedTvSeason != null)
        assertThat("uuids don't match", UUID.fromString(persistedTvSeason!!.uuid) == tvSeason.uuid)
    }

    @Test
    fun addMediaTvEpisode() = runTest {
        // GIVEN a tv episode
        // ------------------------------------------
        val tvEpisode = aTvEpisode().build()

        // WHEN it is added to the media repository
        // ------------------------------------------
        mediaRepository.addMedia(tvEpisode)

        // THEN the tv episode is persisted in the database
        // ------------------------------------------
        val persistedTvEpisode = mediaDao.getTvEpisodeById(tvEpisode.uuid.toString())
        assertThat("persisted tv episode is null", persistedTvEpisode != null)
        assertThat("uuids don't match", UUID.fromString(persistedTvEpisode!!.uuid) == tvEpisode.uuid)
    }
}
