package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("dates")
    val dates: DateRange?
)

data class DateRange(
    @SerializedName("maximum")
    val maximum: String,

    @SerializedName("minimum")
    val minimum: String
)