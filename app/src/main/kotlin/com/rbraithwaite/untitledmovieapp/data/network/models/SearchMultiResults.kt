package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class SearchMultiResults(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<SearchMultiResult>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

sealed interface SearchMultiResult {
    data class Movie(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("id")
        val id: Long,
        @SerializedName("title")
        val title: String,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_title")
        val originalTitle: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("popularity")
        val popularity: Float,
        @SerializedName("release_date")
        val releaseDate: String,
        @SerializedName("video")
        val video: Boolean,
        @SerializedName("vote_average")
        val voteAverage: Float,
        @SerializedName("vote_count")
        val voteCount: Int
    ): SearchMultiResult

    data class TvShow(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("original_language")
        val originalLanguage: String,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("poster_path")
        val posterPath: String?,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("genre_ids")
        val genreIds: List<Int>,
        @SerializedName("popularity")
        val popularity: Float,
        @SerializedName("first_air_date")
        val firstAirDate: String,
        @SerializedName("vote_average")
        val voteAverage: Float,
        @SerializedName("vote_count")
        val voteCount: Int,
        @SerializedName("origin_country")
        val originCountry: List<String>
    ): SearchMultiResult

    data class Person(
        @SerializedName("adult")
        val adult: Boolean,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("original_name")
        val originalName: String,
        @SerializedName("media_type")
        val mediaType: String,
        @SerializedName("popularity")
        val popularity: Float,
        @SerializedName("gender")
        val gender: Int,
        @SerializedName("known_for_department")
        val knownForDepartment: String,
        @SerializedName("profile_path")
        val profilePath: String?,
        @SerializedName("known_for")
        val knownFor: List<SearchMultiResult>
    ): SearchMultiResult
}

class SearchMultiResultDeserializer: JsonDeserializer<SearchMultiResult> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SearchMultiResult {
        return try {
            val deserializeType = when (json!!.asJsonObject.get("media_type").asString) {
                "movie" -> SearchMultiResult.Movie::class.java
                "tv" -> SearchMultiResult.TvShow::class.java
                "person" -> SearchMultiResult.Person::class.java
                else -> throw JsonParseException("Unknown search multi media_type")
            }

            context!!.deserialize(json, deserializeType)
        } catch (e: Exception) {
            throw JsonParseException(e)
        }
    }
}