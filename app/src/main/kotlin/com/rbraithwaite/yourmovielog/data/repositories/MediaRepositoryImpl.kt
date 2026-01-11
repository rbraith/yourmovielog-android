package com.rbraithwaite.yourmovielog.data.repositories

import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.SearchResult
import com.rbraithwaite.yourmovielog.core.repositories.MediaRepository
import com.rbraithwaite.yourmovielog.data.database.dao.MediaDao
import com.rbraithwaite.yourmovielog.data.network.TmdbApiV3
import com.rbraithwaite.yourmovielog.data.network.models.Movie
import com.rbraithwaite.yourmovielog.data.network.models.TvShow
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toEntity
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toTmdbDataMovie
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toTmdbDataTvShow
import com.rbraithwaite.yourmovielog.di.SingletonModule.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.rbraithwaite.yourmovielog.core.data.Movie as CoreMovie

class MediaRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val externalScope: CoroutineScope,
    private val tmdbApiV3: TmdbApiV3,
    private val mediaDao: MediaDao
) : MediaRepository {
    override suspend fun addMedia(media: Media) {
        externalScope.launch(ioDispatcher) {
            when (media) {
                is CoreMovie -> mediaDao.insertMovie(media.toEntity())
                else -> TODO("Not yet implemented")
            }
        }.join()
    }

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
