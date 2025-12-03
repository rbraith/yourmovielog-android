package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toReview
import com.rbraithwaite.untitledmovieapp.data.database.dao.CustomMediaDao
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
    private val customMediaDao: CustomMediaDao,
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

    override suspend fun upsertReviews(vararg reviews: Review) {
        launchExternal {
            val entities = reviews.map { it.toEntity() }.toTypedArray()
            reviewDao.upsertReviews(*entities)
        }
    }

    // TODO [24-01-21 1:33a.m.] -- I need a transaction helper here to wrap the multiple dao calls.
    override suspend fun getAllReviews(extras: Set<KClass<out Review.Extras>>): List<Review> {
        var reviews = reviewDao.getAllReviews().map { it.toReview() }

        // TODO [25-12-3 5:34p.m.] broken.
//        for (extrasType in extras) {
//            when (extrasType) {
//                Review.Extras.RelatedMedia::class -> {
//                    reviews = applyRelatedMediaExtraDataTo(reviews)
//                }
//            }
//        }

        return reviews
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

    private suspend fun updateWithTmdbMovieRelatedMedia(
        groupedReviewsByMediaType: Map<KClass<out Review.MediaType>, List<Review>>
    ): Map<KClass<out Review.MediaType>, List<Review>> {
        val groupKey = Review.MediaType.TmdbMovie::class
        val reviewGroup =
            groupedReviewsByMediaType[groupKey]
                ?: return groupedReviewsByMediaType

        val movieIds = reviewGroup.map { (it.mediaType as Review.MediaType.TmdbMovie).id }
        val movies = tmdbDao.findTmdbLiteMoviesById(movieIds)

        val reviewsWithMovies = reviewGroup.map { review ->
            val relatedMovie = movies.firstOrNull {
                it.tmdbMovie.id == (review as Review.MediaType.TmdbMovie).id
            }

            if (relatedMovie == null) {
                review
            } else {
                review.withExtras(
                    Review.Extras.RelatedMedia.Tmdb(relatedMovie.toTmdbLiteMovie())
                )
            }
        }

        return groupedReviewsByMediaType.toMutableMap().apply {
            put(groupKey, reviewsWithMovies)
        }
    }
}