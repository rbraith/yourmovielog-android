package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.database.entities.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import com.rbraithwaite.untitledmovieapp.data.database.entities.combined.TmdbLiteMovieWithGenres
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
        val entity: ReviewEntity,
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
            .let { updateWithTmdbMovieRelatedMedia(it) }


        return groupedByMediaType.entries.flatMap { it.value }.sortedBy { it.entity.id }
    }

    private suspend fun updateWithCustomRelatedMedia(
        groupedReviewsByMediaType: Map<String, List<ReviewEntityAndCoreData>>
    ): Map<String, List<ReviewEntityAndCoreData>> {
        val customMediaTypeKey = ReviewEntity.Type.CUSTOM.value
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

    private suspend fun updateWithTmdbMovieRelatedMedia(
        groupedReviewsByMediaType: Map<String, List<ReviewEntityAndCoreData>>
    ): Map<String, List<ReviewEntityAndCoreData>> {
        val mediaTypeKey = ReviewEntity.Type.TMDB_MOVIE.value
        val reviewGroup = groupedReviewsByMediaType[mediaTypeKey] ?: return groupedReviewsByMediaType

        val movieIds = reviewGroup.map { (entity, _) -> entity.mediaId }
        val movies = mediaDao.findTmdbLiteMoviesById(movieIds)

        val reviewsWithMovies = reviewGroup.map { entityAndCore ->
            val relatedMovie = movies.firstOrNull { it.tmdbMovie.id == entityAndCore.entity.mediaId }

            if (relatedMovie == null) {
                entityAndCore
            } else {
                entityAndCore.copy(
                    coreData = entityAndCore.coreData.withExtras(
                        MediaReview.Extras.RelatedMedia.Tmdb(relatedMovie.toTmdbLiteMovie())
                    )
                )
            }
        }

        return groupedReviewsByMediaType.toMutableMap().apply {
            put(mediaTypeKey, reviewsWithMovies)
        }
    }

    private fun TmdbLiteMovieWithGenres.toTmdbLiteMovie(): TmdbLite.Movie {
        with (this.tmdbMovie) {
            return TmdbLite.Movie(
                id = this.id,
                title = this.title,
                overview = this.overview,
                posterPath = this.posterPath,
                genreIds = this@toTmdbLiteMovie.genreIds.map { it.genreId },
                popularity = this.popularity,
                releaseDate = this.releaseDate,
                voteAverage = this.voteAverage,
                voteCount = this.voteCount
            )
        }
    }

    private fun ReviewEntity.toMediaReview(): MediaReview {
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
                mediaType = ReviewEntity.Type.CUSTOM,
                mediaId = customMediaId
            )

            mediaDao.addReview(reviewEntity)
        }
    }
}

fun MediaReview.toEntityForMedia(mediaType: ReviewEntity.Type, mediaId: Long): ReviewEntity {
    return ReviewEntity(
        id = id.toLong(),
        mediaId = mediaId,
        mediaType = mediaType.value,
        rating = rating,
        review = review,
        reviewDate = reviewDate,
        watchContext = watchContext
    )
}