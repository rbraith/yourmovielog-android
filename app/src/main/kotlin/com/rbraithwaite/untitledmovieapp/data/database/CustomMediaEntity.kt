package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CustomMediaEntity.Contract.TABLE_NAME)
data class CustomMediaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = Contract.Columns.TITLE) val title: String
) {
    object Contract {
        const val TABLE_NAME = "custom_media"

        object Columns {
            const val TITLE = "title"
        }
    }
}