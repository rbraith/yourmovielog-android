package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Long,

    @SerializedName("logo_path")
    val logoPath: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("origin_country")
    val originCountry: String,
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,

    @SerializedName("name")
    val name: String,
)

data class Language(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso6391: String,

    @SerializedName("name")
    val name: String,
)

data class MovieDetailsResponse(
    @SerializedName("adult")
    val isAdult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    // TODO [24-02-13 11:41p.m.] -- what is this type?
//    @SerializedName("belongs_to_collection")
//    val belongsToCollection,

    @SerializedName("budget")
    val budget: Int,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("homepage")
    val homepage: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("imdb_id")
    val imdbId: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("revenue")
    val revenue: Int,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    // append_to_response data
    // -------------------------------------------
    @SerializedName("credits")
    val credits: Credits?,

    @SerializedName("external_ids")
    val externalIds: ExternalIds?,

    // TODO [24-02-14 12:18a.m.] -- what is this type?
//    @SerializedName("images")
//    val images:

    @SerializedName("keywords")
    val keywords: TmdbKeywords?,

    @SerializedName("recommendations")
    val recommendations: SearchMultiResponse?,

    @SerializedName("similar")
    val similar: DiscoverMovieResponse?,

    @SerializedName("videos")
    val videos: Videos?,

    @SerializedName("watch/providers")
    val watchProviders: WatchProviders?
)
