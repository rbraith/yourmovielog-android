package com.rbraithwaite.yourmovielog.data.network.append_to_response

import com.rbraithwaite.yourmovielog.data.network.TmdbApiV3
import com.rbraithwaite.yourmovielog.data.network.models.MovieDetailsResponse

/**
 * Valid movie detail append_to_response values.
 */
enum class AppendMovieDetails(val apiString: String) {
    CREDITS("credits"),
    EXTERNAL_IDS("external_ids"),
    IMAGES("images"),
    KEYWORDS("keywords"),
    RECOMMENDATIONS("recommendations"),
    SIMILAR("similar"),
    VIDEOS("videos"),
    WATCH_PROVIDERS("watch/providers");

    companion object {
        fun all(): Set<AppendMovieDetails> {
            return AppendMovieDetails.values().toSet()
        }
    }
}

/**
 * Enforcing a valid appendToResponse value.
 */
suspend fun TmdbApiV3.getMovieDetails(
    movieId: Long,
    // REFACTOR [24-02-11 4:57p.m.] -- hardcoded language string.
    language: String = "en-US",
    appendToResponse: Set<AppendMovieDetails>? = null
): Result<MovieDetailsResponse> {
    return getMovieDetails(
        movieId,
        language,
        appendToResponse?.joinToString(",") { it.apiString }
    )
}