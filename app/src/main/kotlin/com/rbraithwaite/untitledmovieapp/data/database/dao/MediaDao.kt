package com.rbraithwaite.untitledmovieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rbraithwaite.untitledmovieapp.data.database.entities.MediaMovieEntity

@Dao
abstract class MediaDao {
    @Insert
    abstract suspend fun insertMovie(movie: MediaMovieEntity)
}