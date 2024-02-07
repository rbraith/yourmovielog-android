package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import com.rbraithwaite.untitledmovieapp.core.repositories.TmdbRepository
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiResult
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toTmdbLiteMovie
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TmdbRepositoryImpl @Inject constructor(
    private val tmdbDao: TmdbDao,
    private val tmdbApiV3: TmdbApiV3,
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher
): TmdbRepository {

    // *********************************************************
    // TmdbRepository
    // *********************************************************
    //region TmdbRepository

    override suspend fun upsertTmdbLite(vararg tmdbLite: TmdbLite) {
        launchExternal {
            val movies = tmdbLite.mapNotNull { it as? TmdbLite.Movie }
            val movieEntities = movies.map { it.toEntity() }.toTypedArray()

            tmdbDao.upsertTmdbLiteMovies(*movieEntities)
            movies.forEach {
                tmdbDao.upsertGenreIdsForMovie(it.id, it.genreIds)
            }
        }
    }

    override suspend fun findTmdbLite(searchCriteria: String): List<TmdbLite> {
        val apiSearchResult = tmdbApiV3.searchMulti(
            query = searchCriteria
        )

        // TODO [24-02-1 12:08a.m.] -- handle result failure and NetworkErrors,
        //  likely need to return result from repo.
        return if (apiSearchResult.isSuccess) {
            val resultList = apiSearchResult.getOrNull()?.results ?: emptyList()

            // TODO [24-02-1 12:09a.m.] -- only handling movies atm.
            resultList
                .mapNotNull { it as? SearchMultiResult.Movie }
                .map { it.toTmdbLiteMovie() }
        } else {
            emptyList()
        }
    }

    //endregion


    // REFACTOR [24-01-31 11:28p.m.] -- duplicated in ReviewRepositoryImpl.
    private suspend fun launchExternal(block: suspend () -> Unit) {
        externalScope.launch(coroutineDispatcher) {
            block()
        }.join()
    }
}