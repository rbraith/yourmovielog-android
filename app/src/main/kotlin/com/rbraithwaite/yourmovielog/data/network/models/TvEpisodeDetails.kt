package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName



data class TvEpisodeDetails(
    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("crew")
    val crew: List<TvSeasonCrew>,

    @SerializedName("episode_number")
    val episodeNumber: Int,

    @SerializedName("guest_stars")
    val guestStars: List<TvSeasonGuestStar>,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("production_code")
    val productionCode: String,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("season_number")
    val seasonNumber: Int,

    @SerializedName("still_path")
    val stillPath: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    // TODO [24-02-23 9:49p.m.] -- append_to_response stuff
    //  I got lazy on this one.
)
