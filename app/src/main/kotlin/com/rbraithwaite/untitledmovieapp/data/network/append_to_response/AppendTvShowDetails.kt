package com.rbraithwaite.untitledmovieapp.data.network.append_to_response

import com.rbraithwaite.untitledmovieapp.data.network.TmdbApiV3
import com.rbraithwaite.untitledmovieapp.data.network.models.TvShowDetails

/**
 * Valid tv show detail append_to_response values.
 */
enum class AppendTvShowDetails(val apiString: String) {
    AGGREGATE_CREDITS("aggregate_credits"),
    CONTENT_RATINGS("content_ratings"),
    EXTERNAL_IDS("external_ids"),
    KEYWORDS("keywords"),
    RECOMMENDATIONS("recommendations"),
    SIMILAR("similar"),
    VIDEOS("videos"),
    WATCH_PROVIDERS("watch/providers");

    companion object {
        fun all(): Set<AppendTvShowDetails> {
            return AppendTvShowDetails.values().toSet()
        }
    }
}

/**
 * Enforcing a valid appendToResponse value.
 */
suspend fun TmdbApiV3.getTvShowDetails(
    seriesId: Long,
    // REFACTOR [24-02-11 4:57p.m.] -- hardcoded language string.
    language: String = "en-US",
    appendToResponse: Set<AppendTvShowDetails>? = null
): Result<TvShowDetails> {
    return getTvShowDetails(
        seriesId,
        language,
        appendToResponse?.joinToString(",") { it.apiString }
    )
}