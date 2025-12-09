package com.rbraithwaite.yourmovielog.core.data

import java.time.LocalDate

data class ReviewDate(
    val year: Int,
    val month: Int?,
    val day: Int?
) {
    constructor(localDate: LocalDate) : this(
        year = localDate.year,
        // REFACTOR [23-09-14 10:48p.m.] -- should probably use 1-12 to align w/ java.time.
        month = localDate.monthValue - 1,
        day = localDate.dayOfMonth
    )
}