package com.rbraithwaite.yourmovielog.data.repositories.conversions

import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
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
