package com.rbraithwaite.yourmovielog.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rbraithwaite.yourmovielog.data.database.dao.MediaDao
import com.rbraithwaite.yourmovielog.data.database.dao.ReviewDao
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaReviewEntity
import com.rbraithwaite.yourmovielog.data.database.type_converters.LocalDateConverter
import com.rbraithwaite.yourmovielog.data.database.type_converters.ReviewDateConverter

@Database(
    entities = [
        MediaMovieEntity::class,
        MediaReviewEntity::class,
    ],
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
