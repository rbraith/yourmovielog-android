package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class SearchMultiResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SearchMultiType>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

sealed interface SearchMultiType

class SearchMultiTypeDeserializer: JsonDeserializer<SearchMultiType> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchMultiType {
        return try {
            val deserializeType = when (json!!.asJsonObject.get("media_type").asString) {
                "movie" -> Movie::class.java
                "tv" -> TvShow::class.java
                "person" -> Person::class.java
                else -> throw JsonParseException("Unknown search multi media_type")
            }

            context!!.deserialize(json, deserializeType)
        } catch (e: Exception) {
            throw JsonParseException(e)
        }
    }
}