package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toCustomMovie
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.CustomMediaDao
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO [25-10-26 4:43p.m.] deprecated: delete this.
class CustomMediaRepositoryImpl @Inject constructor(
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher,
    private val customMediaDao: CustomMediaDao
): CustomMediaRepository {

    // *********************************************************
    // CustomMediaRepository
    // *********************************************************
    //region CustomMediaRepository

    override suspend fun upsertCustomMedia(vararg customMedia: CustomMedia) {
        launchExternal {
            // movies
            val movieEntities = customMedia
                .mapNotNull { it as? CustomMedia.Movie }
                .map { it.toEntity() }
                .toTypedArray()

            customMediaDao.upsertCustomMovies(*movieEntities)
        }
    }

    override suspend fun findMedia(searchCriteria: String): List<CustomMedia> {
        val foundMovies = customMediaDao.searchCustomMoviesByTitle(searchCriteria)
        return foundMovies.map { it.toCustomMovie() }
    }

    //endregion

    // REFACTOR [24-01-31 11:28p.m.] -- duplicated in ReviewRepositoryImpl.
    private suspend fun launchExternal(block: suspend () -> Unit) {
        externalScope.launch(coroutineDispatcher) {
            block()
        }.join()
    }
}