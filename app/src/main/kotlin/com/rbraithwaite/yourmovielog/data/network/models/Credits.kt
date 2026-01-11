package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

// REFACTOR [24-02-22 10:01p.m.] -- this should be MovieCredits.
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
    val order: Int,

    /**
     * Relevant to person details (combined credits)
     */
    @SerializedName("media_type")
    val mediaType: String?,
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
    val job: String,

    /**
     * Relevant to person details (combined credits)
     */
    @SerializedName("media_type")
    val mediaType: String?,
)
