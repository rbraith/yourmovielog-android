package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class CountryTimezones(
    @SerializedName("iso_3166_1")
    val countryIso: String,

    @SerializedName("zones")
    val zoneNames: List<String>
)
