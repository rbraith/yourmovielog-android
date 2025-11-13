package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite

// TODO [25-10-26 4:41p.m.] deprecated: delete this.
interface TmdbRepository {
    suspend fun upsertTmdbLite(vararg tmdbLite: TmdbLite)

    suspend fun findTmdbLite(searchCriteria: String): List<TmdbLite>
}