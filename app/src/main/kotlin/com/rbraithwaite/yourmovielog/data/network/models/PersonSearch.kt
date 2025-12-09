package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class PersonSearchResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<Person>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,
)