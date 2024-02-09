package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName

data class CertificationsResponse(
    /**
     * The String keys are the country codes (US, CA, etc)
     */
    @SerializedName("certifications")
    val certifications: Map<String, List<Certification>>
)

data class Certification(
    /**
     * The certification code
     */
    @SerializedName("certification")
    val certification: String,

    /**
     * A description of this certification.
     */
    @SerializedName("meaning")
    val meaning: String,

    /**
     * For sorting certifications within the same country. The higher the order, the more
     * restricted the certification.
     */
    @SerializedName("order")
    val order: Int,
)