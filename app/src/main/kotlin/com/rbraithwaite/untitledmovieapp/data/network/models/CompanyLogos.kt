package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class CompanyLogo(
    @SerializedName("aspect_ratio")
    val aspectRatio: Float,

    @SerializedName("file_path")
    val filePath: String,

    @SerializedName("height")
    val height: Int,

    @SerializedName("id")
    val id: String,

    @SerializedName("file_type")
    val fileType: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("width")
    val width: Int
)

data class CompanyLogos(
    @SerializedName("id")
    val companyId: Long,

    @SerializedName("logos")
    val logos: List<CompanyLogo>,
)