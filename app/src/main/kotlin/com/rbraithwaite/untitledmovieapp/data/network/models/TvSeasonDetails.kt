package com.rbraithwaite.untitledmovieapp.data.network.models

import com.google.gson.annotations.SerializedName


data class TvSeasonCrew(
    @SerializedName("job")
    val job: String,

    @SerializedName("department")
    val department: String,

    @SerializedName("credit_id")
    val creditId: String,

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
)

data class TvSeasonGuestStar(
    @SerializedName("character")
    val character: String,

    @SerializedName("credit_id")
    val creditId: String,

    @SerializedName("order")
    val order: Int,

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
)

data class TvSeasonEpisode(
    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episode_number")
    val episodeNumber: Int,

    @SerializedName("episode_type")
    val episodeType: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

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

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("crew")
    val crew: List<TvSeasonCrew>,

    @SerializedName("guest_stars")
    val guestStars: List<TvSeasonGuestStar>,
)

data class TvSeasonDetails(
    @SerializedName("_id")
    val _id: String,

    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episodes")
    val episodes: List<TvSeasonEpisode>,

    @SerializedName("name")
    val name: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("season_number")
    val seasonNumber: Int,

    @SerializedName("vote_average")
    val voteAverage: Float,

    // append_to_response data
    // -------------------------------------------
    @SerializedName("aggregate_credits")
    val aggregateCredits: TvSeasonCredits?,

    @SerializedName("external_ids")
    val externalIds: ExternalIds?,

    @SerializedName("videos")
    val videos: Videos?,

    // TODO [24-02-23 9:25p.m.] -- what type is images?
//    @SerializedName("images")
//    val images: Model7?,

    @SerializedName("watch/providers")
    val watchProviders: WatchProviders?
)

data class TvSeasonCastMember(
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

data class TvSeasonCrewMember(
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

data class TvSeasonCredits(
    @SerializedName("cast")
    val cast: List<TvSeasonCastMember>,

    @SerializedName("crew")
    val crew: List<TvSeasonCrewMember>,
)

