package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class ExternalIds(
    @SerializedName("imdb_id")
    val imdbId: String?,

    @SerializedName("wikidata_id")
    val wikidataId: String?,

    @SerializedName("facebook_id")
    val facebookId: String?,

    @SerializedName("instagram_id")
    val instagramId: String?,

    @SerializedName("twitter_id")
    val twitterId: String?,

    @SerializedName("freebase_mid")
    val freebaseMid: String?,

    @SerializedName("freebase_id")
    val freebaseId: String?,

    @SerializedName("tvrage_id")
    val tvrageId: Int?,

    @SerializedName("tiktok_id")
    val tiktokId: String?,

    @SerializedName("youtube_id")
    val youtubeId: String?,

    @SerializedName("tvdb_id")
    val tvdbId: Int?,
)