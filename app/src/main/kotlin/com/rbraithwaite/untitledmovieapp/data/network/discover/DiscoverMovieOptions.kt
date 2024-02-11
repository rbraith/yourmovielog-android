package com.rbraithwaite.untitledmovieapp.data.network.discover

import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.DiscoverMovieResponse

/**
 * the "/discover/movie" endpoint has some rules associated with its params, such as what values are
 * allowed for with_watch_monetization_types or sort_by. This options class is to help enforce those rules.
 */
class DiscoverMovieOptions {
    var certification: String? = null
        private set
    var certificationGte: String? = null
        private set
    var certificationLte: String? = null
        private set
    var certificationCountry: String? = null
        private set
    fun certificationOptions(certificationCountry: String, optionsBlock: CertificationOptions.()->Unit) {
        CertificationOptions(certificationCountry).optionsBlock()
    }

    var includeAdult: Boolean = false
    var includeVideo: Boolean = false
    // REFACTOR [24-02-10 10:04p.m.] -- hardcoded language.
    var language: String = "en-US"
    var page: Int = 1
    var primaryReleaseYear: Int? = null
    var primaryReleaseDateGte: String? = null
    var primaryReleaseDateLte: String? = null
    var region: String? = null
    var releaseDateGte: String? = null
    var releaseDateLte: String? = null
    var voteAverageGte: Float? = null
    var voteAverageLte: Float? = null
    var voteCountGte: Float? = null
    var voteCountLte: Float? = null
    var withOriginalCountry: String? = null
    var withOriginLanguage: String? = null
    var withRuntimeGte: Int? = null
    var withRuntimeLte: Int? = null
    var withoutCompanies: String? = null
    var withoutGenres: String? = null
    var withoutKeywords: String? = null
    var withoutWatchProviders: String? = null
    var year: Int? = null
    var sortBy: SortBy = SortBy.POPULARITY_DESC

    var withCast: String? = null
        private set
    fun withCast(logic: Logic = Logic.AND, vararg cast: String) {
        withCast = if (cast.isEmpty()) {
            null
        } else {
            formatLogic(logic, cast.toList())
        }
    }

    var withCompanies: String? = null
        private set
    fun withCompanies(logic: Logic = Logic.AND, vararg companies: String) {
        withCompanies = if (companies.isEmpty()) {
            null
        } else {
            formatLogic(logic, companies.toList())
        }
    }

    var withCrew: String? = null
        private set
    fun withCrew(logic: Logic = Logic.AND, vararg crew: String) {
        withCrew = if (crew.isEmpty()) {
            null
        } else {
            formatLogic(logic, crew.toList())
        }
    }

    var withGenres: String? = null
        private set
    fun withGenres(logic: Logic = Logic.AND, vararg genres: String) {
        withGenres = if (genres.isEmpty()) {
            null
        } else {
            formatLogic(logic, genres.toList())
        }
    }

    var withKeywords: String? = null
        private set
    fun withKeywords(logic: Logic = Logic.AND, vararg keywords: String) {
        withKeywords = if (keywords.isEmpty()) {
            null
        } else {
            formatLogic(logic, keywords.toList())
        }
    }

    var withPeople: String? = null
        private set
    fun withPeople(logic: Logic = Logic.AND, vararg people: String) {
        withPeople = if (people.isEmpty()) {
            null
        } else {
            formatLogic(logic, people.toList())
        }
    }

    var withReleaseType: String? = null
        private set
    fun withReleaseType(
        logic: Logic = Logic.AND,
        vararg releaseTypes: WatchMonetizationType
    ) {
        withReleaseType = if (releaseTypes.isEmpty()) {
            null
        } else {
            formatLogic(logic, releaseTypes.toSet().map { it.apiString }.toList())
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


    enum class Logic {
        AND,
        OR
    }

    enum class ReleaseType(val apiString: String) {
        PREMIERE("1"),
        THEATRICAL_LIMITED("2"),
        THEATRICAL("3"),
        DIGITAL("4"),
        PHYSICAL("5"),
        TV("6")
    }

    enum class WatchMonetizationType(val apiString: String) {
        FLAT_RATE("flatrate"),
        FREE("free"),
        ADS("ads"),
        RENT("rent"),
        BUY("buy")
    }

    enum class SortBy(val apiString: String) {
        ORIGINAL_TITLE_ASC("original_title.asc"),
        ORIGINAL_TITLE_DESC("original_title.desc"),
        POPULARITY_DESC("popularity.desc"),
        POPULARITY_ASC("popularity.asc"),
        REVENUE_ASC("revenue.asc"),
        REVENUE_DESC("revenue.desc"),
        PRIMARY_RELEASE_DATE_ASC("primary_release_date.asc"),
        PRIMARY_RELEASE_DATE_DESC("primary_release_date.desc"),
        TITLE_ASC("title.asc"),
        TITLE_DESC("title.desc"),
        VOTE_AVERAGE_ASC("vote_average.asc"),
        VOTE_AVERAGE_DESC("vote_average.desc"),
        VOTE_COUNT_DESC("vote_count.desc"),
        VOTE_COUNT_ASC("vote_count.asc")
    }

    inner class CertificationOptions(certificationCountry: String) {
        init {
            this@DiscoverMovieOptions.certificationCountry = certificationCountry
        }

        var certification: String?
            get() = this@DiscoverMovieOptions.certification
            set(value) {
                this@DiscoverMovieOptions.certification = value
            }

        var certificationGte: String?
            get() = this@DiscoverMovieOptions.certificationGte
            set(value) {
                this@DiscoverMovieOptions.certificationGte = value
            }

        var certificationLte: String?
            get() = this@DiscoverMovieOptions.certificationLte
            set(value) {
                this@DiscoverMovieOptions.certificationLte = value
            }
    }

    inner class WatchOptions(watchRegion: String) {
        init {
            this@DiscoverMovieOptions.watchRegion = watchRegion
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

    private fun formatLogic(logic: Logic, strings: List<String>): String {
        val sep = when (logic) {
            Logic.AND -> ","
            Logic.OR -> "|"
        }

        return strings.joinToString(sep)
    }
}

suspend fun TmdbApiV3.discoverMovies(
    discoverMovieOptions: DiscoverMovieOptions.()->Unit
): Result<DiscoverMovieResponse> {
    val options = DiscoverMovieOptions().apply(discoverMovieOptions)

    with (options) {
        return discoverMovies(
            certification = certification,
            certificationGte = certificationGte,
            certificationLte = certificationLte,
            certificationCountry = certificationCountry,
            includeAdult = includeAdult,
            includeVideo = includeVideo,
            language = language,
            page = page,
            primaryReleaseYear = primaryReleaseYear,
            primaryReleaseDateGte = primaryReleaseDateGte,
            primaryReleaseDateLte = primaryReleaseDateLte,
            region = region,
            releaseDateGte = releaseDateGte,
            releaseDateLte = releaseDateLte,
            sortBy = sortBy.apiString,
            voteAverageGte = voteAverageGte,
            voteAverageLte = voteAverageLte,
            voteCountGte = voteCountGte,
            voteCountLte = voteCountLte,
            watchRegion = watchRegion,
            withCast = withCast,
            withCompanies = withCompanies,
            withCrew = withCrew,
            withGenres = withGenres,
            withKeywords = withKeywords,
            withOriginCountry = withOriginalCountry,
            withOriginalLanguage = withOriginLanguage,
            withPeople = withPeople,
            withReleaseType = withReleaseType,
            withRuntimeGte = withRuntimeGte,
            withRuntimeLte = withRuntimeLte,
            withWatchMonetizationTypes = withWatchMonetizationTypes,
            withWatchProviders = withWatchProviders,
            withoutCompanies = withoutCompanies,
            withoutGenres = withoutGenres,
            withoutKeywords = withoutKeywords,
            withoutWatchProviders = withoutWatchProviders,
            year = year
        )
    }
}