package com.rbraithwaite.yourmovielog.core.data

import java.time.LocalDateTime
import java.util.UUID

/** Common Media fields */
sealed interface Media {
    /** This media's unique id */
    val uuid: UUID
    /** The datetime this media was created at. */
    val createdAt: LocalDateTime
    /** The datetime this media was last modified at. This will be the same as [createdAt] when the media is created. */
    val modifiedAt: LocalDateTime
    /** The id for the TMDB data related to this media, if any. */
    val tmdbId: String?
}


data class Movie(
    /** The title of the movie */
    val title: String,
    // Media
    override val uuid: UUID,
    override val createdAt: LocalDateTime,
    override val modifiedAt: LocalDateTime,
    override val tmdbId: String?
) : Media


data class TvShow(
    /** The title of the tv show */
    val title: String,
    // Media
    override val uuid: UUID,
    override val createdAt: LocalDateTime,
    override val modifiedAt: LocalDateTime,
    override val tmdbId: String?
) : Media {
    /** A tv show season */
    data class Season(
        /** The season number of the tv show */
        val seasonNumber: Int,
        // Media
        override val uuid: UUID,
        override val createdAt: LocalDateTime,
        override val modifiedAt: LocalDateTime,
        override val tmdbId: String?
    ) : Media

    /** An episode of a tv show */
    data class Episode(
        /** The title of the episode, if it has one */
        val title: String?,
        /** The episode number, within it's season. */
        val episodeNumber: Int,
        // Media
        override val uuid: UUID,
        override val createdAt: LocalDateTime,
        override val modifiedAt: LocalDateTime,
        override val tmdbId: String?
    ) : Media
}


data class MediaWithTmdb(
    val media: Media,
    val tmdbMedia: TmdbData?
)