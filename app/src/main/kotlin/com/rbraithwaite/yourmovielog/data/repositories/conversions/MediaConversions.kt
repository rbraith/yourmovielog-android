package com.rbraithwaite.yourmovielog.data.repositories.conversions

import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.TvShow
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvEpisodeEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvSeasonEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvShowEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

private val DEFAULT_ZONE_OFFSET = ZoneOffset.UTC

fun Movie.toEntity(): MediaMovieEntity {
    return MediaMovieEntity(
        uuid = uuid.toString(),
        title = title,
        createdAt = createdAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        modifiedAt = modifiedAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun MediaMovieEntity.toMovie(): Movie {
    return Movie(
        uuid = UUID.fromString(uuid),
        title = title,
        createdAt = LocalDateTime.ofEpochSecond(createdAt, 0, DEFAULT_ZONE_OFFSET),
        modifiedAt = LocalDateTime.ofEpochSecond(modifiedAt, 0, DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun TvShow.toEntity(): MediaTvShowEntity {
    return MediaTvShowEntity(
        uuid = uuid.toString(),
        title = title,
        createdAt = createdAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        modifiedAt = modifiedAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun MediaTvShowEntity.toTvShow(): TvShow {
    return TvShow(
        uuid = UUID.fromString(uuid),
        title = title,
        createdAt = LocalDateTime.ofEpochSecond(createdAt, 0, DEFAULT_ZONE_OFFSET),
        modifiedAt = LocalDateTime.ofEpochSecond(modifiedAt, 0, DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun TvShow.Season.toEntity(): MediaTvSeasonEntity {
    return MediaTvSeasonEntity(
        uuid = uuid.toString(),
        seasonNumber = seasonNumber,
        createdAt = createdAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        modifiedAt = modifiedAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun MediaTvSeasonEntity.toTvSeason(): TvShow.Season {
    return TvShow.Season(
        uuid = UUID.fromString(uuid),
        seasonNumber = seasonNumber,
        createdAt = LocalDateTime.ofEpochSecond(createdAt, 0, DEFAULT_ZONE_OFFSET),
        modifiedAt = LocalDateTime.ofEpochSecond(modifiedAt, 0, DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun TvShow.Episode.toEntity(): MediaTvEpisodeEntity {
    return MediaTvEpisodeEntity(
        uuid = uuid.toString(),
        title = title,
        episodeNumber = episodeNumber,
        createdAt = createdAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        modifiedAt = modifiedAt.toEpochSecond(DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}

fun MediaTvEpisodeEntity.toTvEpisode(): TvShow.Episode {
    return TvShow.Episode(
        uuid = UUID.fromString(uuid),
        title = title,
        episodeNumber = episodeNumber,
        createdAt = LocalDateTime.ofEpochSecond(createdAt, 0, DEFAULT_ZONE_OFFSET),
        modifiedAt = LocalDateTime.ofEpochSecond(modifiedAt, 0, DEFAULT_ZONE_OFFSET),
        tmdbId = tmdbId
    )
}
