package com.rbraithwaite.yourmovielog.data.database.type_converters

import androidx.room.TypeConverter
import com.rbraithwaite.yourmovielog.core.data.ReviewDate

class ReviewDateConverter {
    @TypeConverter
    fun fromReviewDate(reviewDate: ReviewDate?): String? {
        if (reviewDate == null) {
            return null
        }

        val yearString = reviewDate.year.toString()
        val monthString = reviewDate.month?.toString() ?: "-"
        val dayString = reviewDate.day?.toString() ?: "-"

        return "$yearString/$monthString/$dayString"
    }

    @TypeConverter
    fun fromString(string: String?): ReviewDate? {
        if (string == null) {
            return null
        }

        val split = string.split("/")
        val year = split[0].toInt()
        val month = split[1].toIntOrNull()
        val day = split[2].toIntOrNull()

        return ReviewDate(year, month, day)
    }
}