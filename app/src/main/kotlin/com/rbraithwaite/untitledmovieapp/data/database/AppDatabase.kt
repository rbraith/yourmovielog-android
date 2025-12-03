package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rbraithwaite.untitledmovieapp.data.database.dao.CustomMediaDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieEntity
import com.rbraithwaite.untitledmovieapp.data.database.entities.TmdbLiteMovieGenreJunction
import com.rbraithwaite.untitledmovieapp.data.database.type_converters.LocalDateConverter
import com.rbraithwaite.untitledmovieapp.data.database.type_converters.ReviewDateConverter

@Database(
    entities = [
        MediaMovieEntity::class,
        MediaReviewEntity::class,
        CustomMovieEntity::class,
        ReviewEntity::class,
        TmdbLiteMovieEntity::class,
        TmdbLiteMovieGenreJunction::class],
    version = 1
)
@TypeConverters(
    ReviewDateConverter::class,
    LocalDateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao

    abstract fun tmdbDao(): TmdbDao

    abstract fun customMediaDao(): CustomMediaDao

    abstract fun reviewDao(): ReviewDao
}