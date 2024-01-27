package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CustomMediaEntity::class,
        MediaReviewEntity::class,
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

    abstract fun reviewDao(): ReviewDao
}