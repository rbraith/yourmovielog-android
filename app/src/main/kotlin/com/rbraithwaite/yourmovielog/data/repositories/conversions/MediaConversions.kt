package com.rbraithwaite.yourmovielog.data.repositories.conversions

import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.TvShow
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvEpisodeEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvSeasonEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvShowEntity
import java.time.ZoneOffset

fun Movie.toEntity(): MediaMovieEntity {
    return MediaMovieEntity(
        uuid = uuid.toString(),
        title = title,
        createdAt = createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = modifiedAt.toEpochSecond(ZoneOffset.UTC),
        tmdbId = tmdbId
    )
}

fun TvShow.toEntity(): MediaTvShowEntity {
    return MediaTvShowEntity(
        uuid = uuid.toString(),
        title = title,
        createdAt = createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = modifiedAt.toEpochSecond(ZoneOffset.UTC),
        tmdbId = tmdbId
    )
}

fun TvShow.Season.toEntity(): MediaTvSeasonEntity {
    return MediaTvSeasonEntity(
        uuid = uuid.toString(),
        seasonNumber = seasonNumber,
        createdAt = createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = modifiedAt.toEpochSecond(ZoneOffset.UTC),
        tmdbId = tmdbId
    )
}

fun TvShow.Episode.toEntity(): MediaTvEpisodeEntity {
    return MediaTvEpisodeEntity(
        uuid = uuid.toString(),
        title = title,
        episodeNumber = episodeNumber,
        createdAt = createdAt.toEpochSecond(ZoneOffset.UTC),
        modifiedAt = modifiedAt.toEpochSecond(ZoneOffset.UTC),
        tmdbId = tmdbId
    )
}
