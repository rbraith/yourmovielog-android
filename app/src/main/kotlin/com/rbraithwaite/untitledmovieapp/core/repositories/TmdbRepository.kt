package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite

interface TmdbRepository {
    suspend fun addOrUpdateTmdbLite(vararg tmdbLite: TmdbLite)

    suspend fun findTmdbLite(searchCriteria: String): List<TmdbLite>
}