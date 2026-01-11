package com.rbraithwaite.yourmovielog.data.network.models

import com.google.gson.annotations.SerializedName

data class TvShowCredit(
    @SerializedName("id")
    val id: Long,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("profile_path")
    val profilePath: String,
)

data class TvEpisode(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episode_number")
    val episodeNumber: Int,

    @SerializedName("episode_type")
    val episodeType: String,

    @SerializedName("production_code")
    val productionCode: String,

    @SerializedName("runtime")
    val runtime: Int,

    @SerializedName("season_number")
    val seasonNumber: Int,

    @SerializedName("show_id")
    val showId: Int,

    @SerializedName("still_path")
    val stillPath: String,
)

data class TvSeason(
    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episode_count")
    val episodeCount: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("season_number")
    val seasonNumber: Int,

    @SerializedName("vote_average")
    val voteAverage: Float,
)

data class TvShowDetails(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @SerializedName("created_by")
    val createdBy: List<TvShowCredit>,

    // TODO [24-02-22 9:24p.m.] -- figure out what data this is.
//    @SerializedName("episode_run_time")
//    val episodeRunTime: List</* UNKNOWN */>,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("homepage")
    val homepage: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("in_production")
    val inProduction: Boolean,

    @SerializedName("languages")
    val languages: List<String>,

    @SerializedName("last_air_date")
    val lastAirDate: String,

    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: TvEpisode,

    @SerializedName("name")
    val name: String,

    // TODO [24-02-22 9:55p.m.] -- learn this data type, probably a TvEpisode.
//    @SerializedName("next_episode_to_air")
//    val nextEpisodeToAir: /* None */,

    // REFACTOR [24-02-22 9:53p.m.] -- ProductionCompany needs a different name.
    @SerializedName("networks")
    val networks: List<ProductionCompany>,

    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,

    @SerializedName("origin_country")
    val originCountry: List<String>,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_name")
    val originalName: String,

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

    @SerializedName("seasons")
    val seasons: List<TvSeason>,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,

    @SerializedName("status")
    val status: String,

    @SerializedName("tagline")
    val tagline: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    // append_to_response data
    // -------------------------------------------
    @SerializedName("aggregate_credits")
    val aggregateCredits: TvShowCredits?,

    @SerializedName("content_ratings")
    val contentRatings: TvContentRatings?,

    // TODO [24-02-22 10:07p.m.] -- what type is this?
//    @SerializedName("episode_groups")
//    val episodeGroups: /* UNKNOWN */,

    @SerializedName("external_ids")
    val externalIds: ExternalIds?,

    // TODO [24-02-22 10:07p.m.] -- what type is this?
//    @SerializedName("images")
//    val images: /* UNKNOWN */,

    @SerializedName("keywords")
    val keywords: TvKeywords?,

    @SerializedName("recommendations")
    val recommendations: SearchMultiResponse?,

    @SerializedName("similar")
    val similar: TvShowSearchResponse?,

    @SerializedName("videos")
    val videos: Videos?,

    @SerializedName("watch/providers")
    val watchProviders: WatchProviders?
)

data class TvKeywords(
    @SerializedName("results")
    val results: List<TmdbKeyword>,
)

data class TvShowRole(
    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("character")
    val character: String,

    @SerializedName("episode_count")
    val episodeCount: Int,
)

data class TvShowCastMember(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("known_for_department")
    val knownForDepartment: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("profile_path")
    val profilePath: String,

    @SerializedName("roles")
    val roles: List<TvShowRole>,

    @SerializedName("total_episode_count")
    val totalEpisodeCount: Int,

    @SerializedName("order")
    val order: Int,
)

data class TvShowJob(
    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("job")
    val job: String,

    @SerializedName("episode_count")
    val episodeCount: Int,
)

data class TvShowCrew(
    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("gender")
    val gender: Int,

    @SerializedName("id")
    val id: Long,

    @SerializedName("known_for_department")
    val knownForDepartment: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("profile_path")
    val profilePath: String?,

    @SerializedName("jobs")
    val jobs: List<TvShowJob>,

    @SerializedName("department")
    val department: String,

    @SerializedName("total_episode_count")
    val totalEpisodeCount: Int,
)

data class TvShowCredits(
    @SerializedName("cast")
    val cast: List<TvShowCastMember>,

    @SerializedName("crew")
    val crew: List<TvShowCrew>,
)

data class TvContentRatings(
    @SerializedName("results")
    val results: List<TvContentRating>,
)

data class TvContentRating(
    // TODO [24-02-22 10:12p.m.] -- What is this type? probably a string list.
//    @SerializedName("descriptors")
//    val descriptors: List</* UNKNOWN */>,

    @SerializedName("iso_3166_1")
    val iso31661: String,

    @SerializedName("rating")
    val rating: String,
)
