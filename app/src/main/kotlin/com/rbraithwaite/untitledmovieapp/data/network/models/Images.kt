package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("profiles")
    val profiles: List<ImageProfile>,
)

data class ImageProfile(
    @SerializedName("aspect_ratio")
    val aspectRatio: Float,

    @SerializedName("height")
    val height: Int,

    // TODO [24-02-19 12:40a.m.] -- what is this? probably a string.
//    @SerializedName("iso_639_1")
//    val iso: /* None */,

    @SerializedName("file_path")
    val filePath: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("width")
    val width: Int,
)