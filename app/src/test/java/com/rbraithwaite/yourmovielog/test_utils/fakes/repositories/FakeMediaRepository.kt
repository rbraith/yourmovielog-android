package com.rbraithwaite.yourmovielog.test_utils.fakes.repositories

import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.SearchResult
import com.rbraithwaite.yourmovielog.core.data.TmdbData
import com.rbraithwaite.yourmovielog.core.repositories.MediaRepository
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.TmdbMovieBuilder
import com.rbraithwaite.yourmovielog.test_utils.data_builders.core_data.TmdbTvShowBuilder


class FakeMediaRepository: MediaRepository {

    private val tmdbData: MutableList<TmdbData> = mutableListOf()

    private val mediaList: MutableList<Media> = mutableListOf()

    // *********************************************************
    // State Initialization
    // *********************************************************
    //region State Initialization

    interface Initializer {
        fun withTmdbMovies(vararg movies: TmdbMovieBuilder)
        fun withTmdbTvShows(vararg tvShows: TmdbTvShowBuilder)
    }

    private inner class InitializerImpl: Initializer {
        override fun withTmdbMovies(vararg movies: TmdbMovieBuilder) {
            this@FakeMediaRepository.tmdbData.addAll(movies.map { it.build() })
        }

        override fun withTmdbTvShows(vararg tvShows: TmdbTvShowBuilder) {
            this@FakeMediaRepository.tmdbData.addAll(tvShows.map { it.build() })
        }
    }

    fun initialize(initBlock: Initializer.() -> Unit) {
        clearState()
        InitializerImpl().initBlock()
    }

    fun clearState() {
        tmdbData.clear()
    }

    //endregion State Initialization

    // *********************************************************
    // MediaRepository
    // *********************************************************
    //region MediaRepository

    override suspend fun addMedia(media: Media) {
        mediaList.add(media)
    }

    override suspend fun searchMulti(query: String): List<SearchResult> {
        val tmdbDataResults = tmdbData.filter {
            when (it) {
                is TmdbData.Movie -> it.title.contains(query)
                is TmdbData.TvShow -> it.name.contains(query)
                else -> false
            }
        }

        return buildList<SearchResult> {
            addAll(tmdbDataResults.map { SearchResult.Tmdb(it) })
        }
    }

    //endregion MediaRepository

    fun getMedia(): List<Media> {
        return mediaList
    }
}