package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast")
    val cast: List<CastMember>,

    @SerializedName("crew")
    val crew: List<CrewMember>
)

data class CastMember(
    @SerializedName("adult")
    val isAdult: Boolean,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("known_for_department")
    val knownFor: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("cast_id")
    val castId: Int,

    @SerializedName("character")
    val characterName: String,

    @SerializedName("credit_id")
    val creditId: String,

    /**
     * Like call sheet order I guess
     */
    @SerializedName("order")
    val order: Int
)


data class CrewMember(
    @SerializedName("adult")
    val isAdult: Boolean,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("known_for_department")
    val knownFor: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("job")
    val job: String
)