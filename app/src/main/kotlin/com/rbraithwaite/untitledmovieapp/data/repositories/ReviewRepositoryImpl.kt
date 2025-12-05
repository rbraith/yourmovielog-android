package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.TmdbDao
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toTmdbLiteMovie
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.reflect.KClass

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDao: ReviewDao,
    private val tmdbDao: TmdbDao,
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher
): ReviewRepository {

    // *********************************************************
    // ReviewRepository
    // *********************************************************
    //region ReviewRepository

    override suspend fun addReview(review: MediaReview, mediaId: UUID) {
        launchExternal {
            reviewDao.insertReview(review.toEntity(mediaId))
        }
    }

    //endregion

    private suspend fun launchExternal(block: suspend () -> Unit) {
        externalScope.launch(coroutineDispatcher) {
            block()
        }.join()
    }

    // TODO [25-12-3 5:34p.m.] broken.
//    private suspend fun applyRelatedMediaExtraDataTo(reviews: List<Review>): List<Review> {
//        return reviews
//            .groupBy { it.mediaType::class }
//            .let { updateWithCustomRelatedMedia(it) }
//            .let { updateWithTmdbMovieRelatedMedia(it) }
//            .entries.flatMap { it.value }.sortedBy { it.id }
//    }

//    private suspend fun updateWithCustomRelatedMedia(
//        groupedReviewsByMediaType: Map<KClass<out Review.MediaType>, List<Review>>
//    ): Map<KClass<out Review.MediaType>, List<Review>> {
        // TODO [25-12-3 5:33p.m.] broken.
//        val groupKey = Review.MediaType.CustomMovie::class
//        val customMovieReviewGroup =
//            groupedReviewsByMediaType[groupKey]
//                ?: return groupedReviewsByMediaType
//
//        val customMovieIds = customMovieReviewGroup.map { (it.mediaType as Review.MediaType.CustomMovie).id }
//        val customMovies = customMediaDao.findCustomMoviesWithIds(customMovieIds)
//
//        val reviewsWithCustomMedia = customMovieReviewGroup.map { review ->
//            val relatedMedia = customMovies.firstOrNull {
//                it.id == (review.mediaType as Review.MediaType.CustomMovie).id
//            }
//
//            if (relatedMedia == null) {
//                review// return unchanged if related media id isn't found
//            } else {
//                review.withExtras(Review.Extras.RelatedMedia.Custom(relatedMedia.toCustomMovie()))
//            }
//        }
//
//        return groupedReviewsByMediaType.toMutableMap().apply {
//            put(groupKey, reviewsWithCustomMedia)
//        }
//    }
}