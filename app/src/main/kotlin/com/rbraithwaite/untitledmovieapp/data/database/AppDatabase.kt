package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CustomMediaEntity::class,
        MediaReviewEntity::class],
    version = 1
)
@TypeConverters(ReviewDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao

    abstract fun reviewDao(): ReviewDao
}