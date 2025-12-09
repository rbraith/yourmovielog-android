package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("popularity")
    val popularity: Float,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("known_for")
    val knownFor: List<SearchMultiType>
): SearchMultiType