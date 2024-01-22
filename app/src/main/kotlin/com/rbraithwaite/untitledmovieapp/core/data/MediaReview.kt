package com.rbraithwaite.untitledmovieapp.core.data

import kotlin.reflect.KClass

typealias MediaReviewExtrasType = KClass<out MediaReview.Extras>

data class MediaReview(
    val id: Int = 0,
    val rating: Int? = null,
    val review: String? = null,
    val reviewDate: ReviewDate? = null,
    val watchContext: String? = null,
    val extras: List<Extras> = emptyList()
) {
    inline fun <reified ExtrasType : Extras> getExtra(): ExtrasType? {
        for (extraData in extras) {
            if (extraData is ExtrasType) {
                return extraData
            }
        }
        return null
    }

    fun <T: Extras> withExtras(extras: T): MediaReview {
        return copy(extras = this.extras.toMutableList().apply { add(extras) })
    }

    sealed interface Extras {
        sealed interface RelatedMedia: Extras {
            data class Custom(val data: CustomMedia): RelatedMedia
            data class Tmdb(val data: TmdbLite): RelatedMedia
        }
    }
}
