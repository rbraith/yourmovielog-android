package com.rbraithwaite.untitledmovieapp.data

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.ReviewDao
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDao: ReviewDao,
    private val mediaDao: MediaDao,
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher
): ReviewRepository {
    private suspend fun launchCoroutine(block: suspend () -> Unit) {
        externalScope.launch(coroutineDispatcher) {
            block()
        }.join()
    }

    private data class ReviewEntityAndCoreData(
        val entity: MediaReviewEntity,
        val coreData: MediaReview
    )

    // TODO [24-01-21 1:33a.m.] -- I need a transaction helper here to wrap the multiple dao calls.
    override suspend fun getAllReviews(extras: Set<KClass<out MediaReview.Extras>>): List<MediaReview> {
        val reviews = reviewDao.getAllReviews()
        if (extras.isEmpty()) {
            return reviews.map { it.toMediaReview() }
        }

        // keep the entity and core data together for now, since the entity has information
        // about the extras.
        var reviewEntitiesAndCoreData = reviews.map { ReviewEntityAndCoreData(it, it.toMediaReview()) }

        // apply extra data to the core data
        for (extrasType in extras) {
            when (extrasType) {
                MediaReview.Extras.RelatedMedia::class -> {
                    reviewEntitiesAndCoreData = applyRelatedMediaExtraDataTo(reviewEntitiesAndCoreData)
                }
            }
        }

        // isolate and return the core data
        return reviewEntitiesAndCoreData.map { it.coreData }
    }

    private suspend fun applyRelatedMediaExtraDataTo(
        entitiesAndReviews: List<ReviewEntityAndCoreData>
    ): List<ReviewEntityAndCoreData> {
        var groupedByMediaType = entitiesAndReviews.groupBy { it.entity.mediaType }

        groupedByMediaType = updateWithCustomRelatedMedia(groupedByMediaType)

        // TODO [24-01-22 12:42a.m.] here, I need to pull tmdb lite movie data from the db
        //  it'll need to be combined with the genre ids in the movie/genre junction.

        return groupedByMediaType.entries.flatMap { it.value }.sortedBy { it.entity.id }
    }

    private suspend fun updateWithCustomRelatedMedia(
        groupedReviewsByMediaType: Map<String, List<ReviewEntityAndCoreData>>
    ): Map<String, List<ReviewEntityAndCoreData>> {
        val customMediaTypeKey = MediaReviewEntity.Type.CUSTOM.value
        val customMediaReviewGroup = groupedReviewsByMediaType[customMediaTypeKey] ?: return groupedReviewsByMediaType

        val customMediaIds = customMediaReviewGroup.map { (entity, _) -> entity.mediaId }
        val customMedia = mediaDao.findCustomMediaWithIds(customMediaIds)

        val reviewsWithCustomMedia = customMediaReviewGroup.map { entityAndCore ->
            val relatedMedia = customMedia.firstOrNull { it.id == entityAndCore.entity.mediaId }

            if (relatedMedia == null) {
                entityAndCore// return unchanged if related media id isn't found
            } else {
                entityAndCore.copy(
                    coreData = entityAndCore.coreData.withExtras(
                        MediaReview.Extras.RelatedMedia.Custom(relatedMedia.toCustomMedia())
                    )
                )
            }
        }

        return groupedReviewsByMediaType.toMutableMap().apply {
            put(customMediaTypeKey, reviewsWithCustomMedia)
        }
    }

    private fun MediaReviewEntity.toMediaReview(): MediaReview {
        return MediaReview(
            id = id.toInt(),
            rating = rating,
            review = review,
            reviewDate = reviewDate,
            watchContext = watchContext
        )
    }

    private fun CustomMediaEntity.toCustomMedia(): CustomMedia {
        return CustomMedia(
            id = id,
            title = title
        )
    }

    override suspend fun addReviewForCustomMedia(review: MediaReview, customMediaId: Long) {
        launchCoroutine {
            val reviewEntity = review.toEntityForMedia(
                mediaType = MediaReviewEntity.Type.CUSTOM,
                mediaId = customMediaId
            )

            mediaDao.addReview(reviewEntity)
        }
    }
}

fun MediaReview.toEntityForMedia(mediaType: MediaReviewEntity.Type, mediaId: Long): MediaReviewEntity {
    return MediaReviewEntity(
        id = id.toLong(),
        mediaId = mediaId,
        mediaType = mediaType.value,
        rating = rating,
        review = review,
        reviewDate = reviewDate,
        watchContext = watchContext
    )
}