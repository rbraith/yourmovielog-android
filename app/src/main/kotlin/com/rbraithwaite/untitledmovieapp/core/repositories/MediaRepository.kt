package com.rbraithwaite.untitledmovieapp.core.repositories

import com.rbraithwaite.untitledmovieapp.core.data.Media
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult

interface MediaRepository {
    suspend fun addMedia(media: Media)

    /**
     * Search across multiple media types at once: movies, TV shows, people
     *
     * @param query The search query, this will be used in a simple media title or person's name search
     */
    suspend fun searchMulti(query: String): List<SearchResult>
}