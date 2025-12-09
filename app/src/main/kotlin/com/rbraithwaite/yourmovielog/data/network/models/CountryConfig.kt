package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class CountryConfig(
    @SerializedName("iso_3166_1")
    val iso: String,

    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("native_name")
    val nativeName: String,
)
