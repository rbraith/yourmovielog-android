package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class TmdbKeywords(
    @SerializedName("keywords")
    val keywords: List<TmdbKeyword>
)

data class TmdbKeyword(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String
)