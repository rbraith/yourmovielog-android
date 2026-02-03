package com.rbraithwaite.yourmovielog.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rbraithwaite.yourmovielog.data.database.entities.MediaMovieEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvEpisodeEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvSeasonEntity
import com.rbraithwaite.yourmovielog.data.database.entities.MediaTvShowEntity

@Dao
abstract class MediaDao {
    @Insert
    abstract suspend fun insertMovie(movie: MediaMovieEntity)

    @Insert
    abstract suspend fun insertTvShow(tvShow: MediaTvShowEntity)

    @Insert
    abstract suspend fun insertTvSeason(tvSeason: MediaTvSeasonEntity)

    @Insert
    abstract suspend fun insertTvEpisode(tvEpisode: MediaTvEpisodeEntity)

    @Query(
        "SELECT * FROM ${MediaTvShowEntity.Contract.TABLE_NAME} " +
            "WHERE ${MediaTvShowEntity.Contract.Columns.UUID} = :id"
    )
    abstract suspend fun getTvShowById(id: String): MediaTvShowEntity?

    @Query(
        "SELECT * FROM ${MediaTvSeasonEntity.Contract.TABLE_NAME} " +
            "WHERE ${MediaTvSeasonEntity.Contract.Columns.UUID} = :id"
    )
    abstract suspend fun getTvSeasonById(id: String): MediaTvSeasonEntity?

    @Query(
        "SELECT * FROM ${MediaTvEpisodeEntity.Contract.TABLE_NAME} " +
            "WHERE ${MediaTvEpisodeEntity.Contract.Columns.UUID} = :id"
    )
    abstract suspend fun getTvEpisodeById(id: String): MediaTvEpisodeEntity?
}
