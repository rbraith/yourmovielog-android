package com.rbraithwaite.untitledmovieapp.data.database

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    companion object {
        private const val NULL_DATE = 0L
    }

    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): Long {
        return localDate?.toEpochDay() ?: NULL_DATE
    }

    @TypeConverter
    fun fromLong(longVal: Long): LocalDate? {
        return if (longVal == NULL_DATE)  null else LocalDate.ofEpochDay(longVal)
    }
}