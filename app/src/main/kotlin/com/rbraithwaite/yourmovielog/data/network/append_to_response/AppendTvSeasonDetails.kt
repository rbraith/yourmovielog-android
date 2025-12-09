package com.rbraithwaite.yourmovielog.data.network.append_to_response

import com.rbraithwaite.yourmovielog.data.network.TmdbApiV3
import com.rbraithwaite.yourmovielog.data.network.models.TvSeasonDetails

/**
 * Valid tv show detail append_to_response values.
 */
enum class AppendTvSeasonDetails(val apiString: String) {
    AGGREGATE_CREDITS("aggregate_credits"),
    EXTERNAL_IDS("external_ids"),
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
suspend fun TmdbApiV3.getTvSeasonDetails(
    seriesId: Long,
    seasonNumber: Int,
    // REFACTOR [24-02-11 4:57p.m.] -- hardcoded language string.
    language: String = "en-US",
    appendToResponse: Set<AppendTvSeasonDetails>? = null
): Result<TvSeasonDetails> {
    return getTvSeasonDetails(
        seriesId,
        seasonNumber,
        language,
        appendToResponse?.joinToString(",") { it.apiString }
    )
}