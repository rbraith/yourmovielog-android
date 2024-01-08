package com.rbraithwaite.untitledmovieapp.core.data

import kotlin.reflect.KClass

typealias MediaReviewHydrationType = KClass<out MediaReview.Hydration>

data class MediaReview(
    val id: Int = 0,
    val rating: Int? = null,
    val review: String? = null,
    val reviewDate: ReviewDate? = null,
    val watchContext: String? = null,
    val hydrations: List<Hydration> = emptyList()
) {
    // REFACTOR [24-01-6 4:35p.m.] -- I can make a generic HydratableType maybe?.
    inline fun <reified HydrationType : Hydration> getHydration(): HydrationType? {
        for (hydration in hydrations) {
            if (hydration is HydrationType) {
                return hydration
            }
        }
        return null
    }

    fun <T: Hydration> withHydration(hydration: T): MediaReview {
        return copy(hydrations = hydrations.toMutableList().apply { add(hydration) })
    }

    sealed interface Hydration {
        sealed interface RelatedMedia: Hydration {
            data class Custom(val data: CustomMedia): RelatedMedia
            data class Tmdb(val data: TmdbLite): RelatedMedia
        }
    }
}
