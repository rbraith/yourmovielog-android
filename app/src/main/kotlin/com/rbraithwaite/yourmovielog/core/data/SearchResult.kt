package com.rbraithwaite.yourmovielog.core.data

sealed interface SearchResult {
    /**
     * A search result with local [Media] data, which may or may not also have related [TmdbData]
     */
    data class Media(val value: MediaWithTmdb): SearchResult

    /**
     * A search result that is [TmdbData] only (no related local [Media] data)
     */
    data class Tmdb(val value: TmdbData): SearchResult
}