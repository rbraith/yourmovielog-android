package com.rbraithwaite.untitledmovieapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CustomMovieEntity.Contract.TABLE_NAME)
data class CustomMovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Contract.Columns.ID)
    val id: Long = 0,

    @ColumnInfo(name = Contract.Columns.TITLE)
    val title: String
) {
    object Contract {
        const val TABLE_NAME = "custom_movie"

        object Columns {
            const val ID = "id"
            const val TITLE = "title"
        }
    }
}