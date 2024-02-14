package com.rbraithwaite.untitledmovieapp.data.network.models

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
    val twitterId: String?
)