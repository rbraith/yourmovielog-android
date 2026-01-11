package com.rbraithwaite.yourmovielog.data.network.append_to_response

import com.rbraithwaite.yourmovielog.data.network.TmdbApiV3
import com.rbraithwaite.yourmovielog.data.network.models.PersonDetailsResponse

/**
 * Valid person detail append_to_response values.
 */
enum class AppendPersonDetails(val apiString: String) {
    EXTERNAL_IDS("external_ids"),
    IMAGES("images"),
    COMBINED_CREDITS("combined_credits");

    companion object {
        fun all(): Set<AppendPersonDetails> {
            return AppendPersonDetails.values().toSet()
        }
    }
}

/**
 * Enforcing a valid appendToResponse value.
 */
suspend fun TmdbApiV3.getPersonDetails(
    personId: Long,
    // REFACTOR [24-02-11 4:57p.m.] -- hardcoded language string.
    language: String = "en-US",
    appendToResponse: Set<AppendPersonDetails>? = null
): Result<PersonDetailsResponse> {
    return getPersonDetails(
        personId,
        language,
        appendToResponse?.joinToString(",") { it.apiString }
    )
}
