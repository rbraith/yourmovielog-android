package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toCustomMovie
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.CustomMediaDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CustomMediaRepositoryImpl @Inject constructor(
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher,
    private val customMediaDao: CustomMediaDao
): CustomMediaRepository {
    override suspend fun addOrUpdateCustomMedia(vararg customMedia: CustomMedia) {
        launchExternal {
            // movies
            val movieEntities = customMedia
                .mapNotNull { it as? CustomMedia.Movie }
                .map { it.toEntity() }
                .toTypedArray()

            customMediaDao.insertOrUpdateCustomMovies(*movieEntities)
        }
    }

    // REFACTOR [24-01-31 11:28p.m.] -- duplicated in ReviewRepositoryImpl.
    private suspend fun launchExternal(block: suspend () -> Unit) {
        externalScope.launch(coroutineDispatcher) {
            block()
        }.join()
    }

    override suspend fun findMedia(searchCriteria: String): List<CustomMedia> {
        val foundMovies = customMediaDao.searchCustomMoviesByTitle(searchCriteria)
        return foundMovies.map { it.toCustomMovie() }
    }
}