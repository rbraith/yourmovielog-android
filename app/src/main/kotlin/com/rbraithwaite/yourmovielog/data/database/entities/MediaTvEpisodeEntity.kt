package com.rbraithwaite.yourmovielog.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MediaTvEpisodeEntity.Contract.TABLE_NAME)
data class MediaTvEpisodeEntity(
    @PrimaryKey
    @ColumnInfo(name = Contract.Columns.UUID)
    val uuid: String,

    @ColumnInfo(name = Contract.Columns.TITLE)
    val title: String?,

    @ColumnInfo(name = Contract.Columns.SEASON_NUMBER)
    val episodeNumber: Int,

    @ColumnInfo(name = Contract.Columns.CREATED_AT)
    val createdAt: Long,

    @ColumnInfo(name = Contract.Columns.MODIFIED_AT)
    val modifiedAt: Long,

    @ColumnInfo(name = Contract.Columns.TMDB_ID)
    val tmdbId: String?
) {
    object Contract {
        const val TABLE_NAME = "media_tv_episode"

        object Columns {
            const val UUID = "uuid"
            const val TITLE = "title"
            const val SEASON_NUMBER = "season_number"
            const val CREATED_AT = "created_at"
            const val MODIFIED_AT = "modified_at"
            const val TMDB_ID = "tmdb_id"
        }
    }
}
