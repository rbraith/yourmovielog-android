package com.rbraithwaite.yourmovielog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity

@Dao
abstract class MediaDao {
    @Insert
    abstract suspend fun insertMovie(movie: MediaMovieEntity)
}
