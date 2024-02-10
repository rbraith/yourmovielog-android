package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class JobsConfig(
    @SerializedName("department")
    val department: String,

    @SerializedName("jobs")
    val jobs: List<String>
)
