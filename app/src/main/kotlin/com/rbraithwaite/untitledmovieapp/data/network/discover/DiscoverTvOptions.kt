package com.rbraithwaite.untitledmovieapp.data.network.discover

import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverTvResponse

/**
 * the "/discover/tv" endpoint has some rules associated with its params, such as what values are
 * allowed for with_watch_monetization_types or sort_by. This options class is to help enforce those rules.
 */
class DiscoverTvOptions {
    var airDateGte: String? = null
    var airDateLte: String? = null
    var firstAirDateYear: Int? = null
    var firstAirDateGte: String? = null
    var firstAirDateLte: String? = null
    var includeAdult: Boolean = false
    var includeNullFirstAirDates: Boolean = false
    // REFACTOR [24-02-11 4:57p.m.] -- hardcoded language string.
    var language: String = "en-US"
    var page: Int = 1
    var screenedTheatrically: Boolean? = null
    var sortBy: SortBy = SortBy.POPULARITY_DESC
    var timezone: String? = null
    var voteAverageGte: Float? = null
    var voteAverageLte: Float? = null
    var voteCountGte: Float? = null
    var voteCountLte: Float? = null
    var withNetworks: Int? = null
    var withOriginCountry: String? = null
    var withOriginalLanguage: String? = null
    var withRuntimeGte: Int? = null
    var withRuntimeLte: Int? = null

    var withStatus: String? = null
        private set
    fun withStatus(logic: Logic, vararg status: Status) {
        withStatus = if (status.isEmpty()) {
            null
        } else {
            formatLogic(logic, status.toSet().map { it.apiString }.toList())
        }
    }

    var withCompanies: String? = null
        private set
    fun withCompanies(logic: Logic, vararg companies: String) {
        withCompanies = if (companies.isEmpty()) {
            null
        } else {
            formatLogic(logic, companies.toList())
        }
    }

    var withGenres: String? = null
        private set
    fun withGenres(logic: Logic, vararg genres: String) {
        withGenres = if (genres.isEmpty()) {
            null
        } else {
            formatLogic(logic, genres.toList())
        }
    }

    var withKeywords: String? = null
        private set
    fun withKeywords(logic: Logic, vararg keywords: String) {
        withKeywords = if (keywords.isEmpty()) {
            null
        } else {
            formatLogic(logic, keywords.toList())
        }
    }

    var withoutCompanies: String? = null
        private set
    fun withoutCompanies(logic: Logic, vararg companies: String) {
        withoutCompanies = if (companies.isEmpty()) {
            null
        } else {
            formatLogic(logic, companies.toList())
        }
    }

    var withoutGenres: String? = null
        private set
    fun withoutGenres(logic: Logic, vararg genres: String) {
        withoutGenres = if (genres.isEmpty()) {
            null
        } else {
            formatLogic(logic, genres.toList())
        }
    }

    var withoutKeywords: String? = null
        private set
    fun withoutKeywords(logic: Logic, vararg keywords: String) {
        withoutKeywords = if (keywords.isEmpty()) {
            null
        } else {
            formatLogic(logic, keywords.toList())
        }
    }

    var withoutWatchProviders: String? = null
        private set
    fun withoutWatchProviders(logic: Logic, vararg watchProviders: String) {
        withoutWatchProviders = if (watchProviders.isEmpty()) {
            null
        } else {
            formatLogic(logic, watchProviders.toList())
        }
    }

    var withType: String? = null
        private set
    fun withType(logic: Logic, vararg types: Type) {
        withType = if (types.isEmpty()) {
            null
        } else {
            formatLogic(logic, types.toSet().map { it.apiString }.toList())
        }
    }

    var watchRegion: String? = null
        private set
    var withWatchMonetizationTypes: String? = null
        private set
    var withWatchProviders: String? = null
        private set
    fun withWatchOptions(watchRegion: String, optionsBlock: WatchOptions.()->Unit) {
        WatchOptions(watchRegion).optionsBlock()
    }

    // REFACTOR [24-02-11 5:26p.m.] -- duplicates DiscoverMovieOptions.
    enum class Logic {
        AND,
        OR
    }

    /**
     * See [TMDB Bible](https://www.themoviedb.org/bible/tv#59f7403f9251416e7100002a)
     */
    enum class Type(val apiString: String) {
        DOCUMENTARY("0"),
        NEWS("1"),
        MINISERIES("2"),
        REALITY("3"),
        SCRIPTED("4"),
        TALK_SHOW("5"),
        VIDEO("6")
    }

    /**
     * See [this forum reply](https://www.themoviedb.org/talk/58b1cfbac3a368077800feb5?language=en-CA)
     */
    enum class Status(val apiString: String) {
        RETURNING("0"),
        PLANNED("1"),
        IN_PRODUCTION("2"),
        ENDED("3"),
        CANCELLED("4"),
        PILOT("5")
    }

    enum class WatchMonetizationType(val apiString: String) {
        FLAT_RATE("flatrate"),
        FREE("free"),
        ADS("ads"),
        RENT("rent"),
        BUY("buy")
    }

    enum class SortBy(val apiString: String) {
        FIRST_AIR_DATE_ASC("first_air_date.asc"),
        FIRST_AIR_DATE_DESC("first_air_date.desc"),
        NAME_ASC("name.asc"),
        NAME_DESC("name.desc"),
        ORIGINAL_NAME_ASC("original_name.asc"),
        ORIGINAL_NAME_DESC("original_name.desc"),
        POPULARITY_DESC("popularity.desc"),
        POPULARITY_ASC("popularity.asc"),
        VOTE_AVERAGE_ASC("vote_average.asc"),
        VOTE_AVERAGE_DESC("vote_average.desc"),
        VOTE_COUNT_DESC("vote_count.desc"),
        VOTE_COUNT_ASC("vote_count.asc")
    }

    inner class WatchOptions(watchRegion: String) {
        init {
            this@DiscoverTvOptions.watchRegion = watchRegion
        }

        fun withWatchMonetizationTypes(
            logic: Logic = Logic.AND,
            vararg watchMonetizationTypes: WatchMonetizationType
        ) {
            withWatchMonetizationTypes = if (watchMonetizationTypes.isEmpty()) {
                null
            } else {
                formatLogic(logic, watchMonetizationTypes.toSet().map { it.apiString }.toList())
            }
        }

        fun withWatchProviders(logic: Logic = Logic.AND, vararg watchProviders: String) {
            withWatchProviders = if (watchProviders.isEmpty()) {
                null
            } else {
                formatLogic(logic, watchProviders.toList())
            }
        }
    }

    // REFACTOR [24-02-11 5:26p.m.] -- duplicates DiscoverMovieOptions.
    private fun formatLogic(logic: Logic, strings: List<String>): String {
        val sep = when (logic) {
            Logic.AND -> ","
            Logic.OR -> "|"
        }

        return strings.joinToString(sep)
    }
}

suspend fun TmdbApiV3.discoverTvShows(
    options: DiscoverTvOptions.() -> Unit
): Result<DiscoverTvResponse> {
    val discoverTvOptions = DiscoverTvOptions().apply(options)

    with (discoverTvOptions) {
        return discoverTvShows(
            airDateGte = airDateGte,
            airDateLte = airDateLte,
            firstAirDateYear = firstAirDateYear,
            firstAirDateGte = firstAirDateGte,
            firstAirDateLte = firstAirDateLte,
            includeAdult = includeAdult,
            includeNullFirstAirDates = includeNullFirstAirDates,
            language = language,
            page = page,
            screenedTheatrically = screenedTheatrically,
            sortBy = sortBy.apiString,
            timezone = timezone,
            voteAverageGte = voteAverageGte,
            voteAverageLte = voteAverageLte,
            voteCountGte = voteCountGte,
            voteCountLte = voteCountLte,
            watchRegion = watchRegion,
            withCompanies = withCompanies,
            withGenres = withGenres,
            withKeywords = withKeywords,
            withNetworks = withNetworks,
            withOriginCountry = withOriginCountry,
            withOriginalLanguage = withOriginalLanguage,
            withRuntimeGte = withRuntimeGte,
            withRuntimeLte = withRuntimeLte,
            withStatus = withStatus,
            withWatchMonetizationTypes = withWatchMonetizationTypes,
            withWatchProviders = withWatchProviders,
            withoutCompanies = withoutCompanies,
            withoutGenres = withoutGenres,
            withoutKeywords = withoutKeywords,
            withoutWatchProviders = withoutWatchProviders,
            withType = withType
        )
    }
}