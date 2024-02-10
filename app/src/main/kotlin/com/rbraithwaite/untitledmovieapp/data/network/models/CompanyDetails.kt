package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class CompanyDetails(
    @SerializedName("description")
    val description: String,

    @SerializedName("headquarters")
    val headquarters: String,

    @SerializedName("homepage")
    val homepageUrl: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("logo_path")
    val logoPath: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("origin_country")
    val originCountry: String,

    @SerializedName("parent_company")
    val parentCompany: String?
)
