package com.rbraithwaite.untitledmovieapp.data.repositories.conversions

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity

fun CustomMovieEntity.toCustomMovie(): CustomMedia.Movie {
    return CustomMedia.Movie(
        id = id,
        title = title
    )
}

fun CustomMedia.Movie.toEntity(): CustomMovieEntity {
    return CustomMovieEntity(
        id = id,
        title = title
    )
}