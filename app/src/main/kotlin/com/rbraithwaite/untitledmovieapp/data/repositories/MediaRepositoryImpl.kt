package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.Movie
import com.rbraithwaite.untitledmovieapp.data.network.models.SearchMultiType
import com.rbraithwaite.untitledmovieapp.data.network.models.TvShow
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toTmdbDataMovie
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toTmdbDataTvShow
import com.rbraithwaite.untitledmovieapp.di.SingletonModule.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val tmdbApiV3: TmdbApiV3
): MediaRepository {
    // TODO [25-10-26 5:16p.m.] how to handle api pagination?.
    override suspend fun searchMulti(query: String): List<SearchResult> = withContext(ioDispatcher) {
        val searchMultiApiResponseResult = tmdbApiV3.searchMulti(query)

        return@withContext if (searchMultiApiResponseResult.isSuccess) {
            val response = searchMultiApiResponseResult.getOrNull() ?: return@withContext emptyList()

            response.results.mapNotNull {
                when (it) {
                    is Movie -> SearchResult.Tmdb(it.toTmdbDataMovie())
                    is TvShow -> SearchResult.Tmdb(it.toTmdbDataTvShow())
                    // TODO [25-11-24 10:43p.m.] Person.
                    // TODO [25-11-24 10:43p.m.] SearchResult.Media.
                    else -> null
                }
            }
        } else {
            emptyList()
        }
    }
}