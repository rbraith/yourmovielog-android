package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName


data class PersonDetailsResponse(
    @SerializedName("adult")
    val adult: Boolean,

    // TODO [24-02-19 12:26a.m.] -- dunno what this field is, probably strings.
//    @SerializedName("also_known_as")
//    val alsoKnownAs: List</* UNKNOWN */>,

    @SerializedName("biography")
    val biography: String,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("deathday")
    val deathday: String?,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("homepage")
    val homepage: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("imdb_id")
    val imdbId: String,

    @SerializedName("known_for_department")
    val knownForDepartment: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("place_of_birth")
    val placeOfBirth: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("profile_path")
    val profilePath: String,

    // append_to_response data
    // -------------------------------------------
    @SerializedName("external_ids")
    val externalIds: ExternalIds?,

    @SerializedName("images")
    val images: Images?,

    @SerializedName("combined_credits")
    val combinedCredits: Credits?,
)