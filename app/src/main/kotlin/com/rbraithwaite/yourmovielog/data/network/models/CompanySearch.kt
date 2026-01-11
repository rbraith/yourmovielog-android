package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class CompanySearchResult(
    @SerializedName("id")
    val id: Long,

    @SerializedName("logo_path")
    val logoPath: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("origin_country")
    val originCountry: String,
)

data class CompanySearchResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: List<CompanySearchResult>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,
)
