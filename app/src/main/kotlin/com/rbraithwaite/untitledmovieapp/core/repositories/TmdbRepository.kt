package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite

interface TmdbRepository {
    suspend fun upsertTmdbLite(vararg tmdbLite: TmdbLite)

    suspend fun findTmdbLite(searchCriteria: String): List<TmdbLite>
}