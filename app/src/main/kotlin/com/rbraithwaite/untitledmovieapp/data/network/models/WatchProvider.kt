package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName


data class WatchProvider(
    @SerializedName("logo_path")
    val logoPath: String,

    @SerializedName("provider_id")
    val providerId: Long,

    @SerializedName("provider_name")
    val providerName: String,

    @SerializedName("display_priority")
    val displayPriority: Int,
)

data class WatchMonetizations(
    @SerializedName("link")
    val link: String,

    @SerializedName("rent")
    val rent: List<WatchProvider>?,

    @SerializedName("buy")
    val buy: List<WatchProvider>?,

    @SerializedName("flatrate")
    val flatrate: List<WatchProvider>?,

    // TODO [24-02-14 12:33a.m.] -- just guessing about this field, should confirm it exists in the api.
    @SerializedName("ads")
    val ads: List<WatchProvider>?,

    // TODO [24-02-14 12:33a.m.] -- just guessing about this field, should confirm it exists in the api.
    @SerializedName("free")
    val free: List<WatchProvider>?,
)

data class WatchProviders(
    /**
     * String key is the ISO 3166-1 country code
     */
    @SerializedName("results")
    val results: Map<String, WatchMonetizations>
)
