package com.rbraithwaite.yourmovielog.data.repositories

import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.data.MediaReviewWithMedia
import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import com.rbraithwaite.yourmovielog.data.database.dao.MediaDao
import com.rbraithwaite.yourmovielog.data.database.dao.ReviewDao
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toEntity
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toMediaReview
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toMovie
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toTvEpisode
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toTvSeason
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toTvShow
import com.rbraithwaite.yourmovielog.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDao: ReviewDao,
    private val mediaDao: MediaDao,
    private val externalScope: CoroutineScope,
    @SingletonModule.IoDispatcher
    private val coroutineDispatcher: CoroutineDispatcher
) : ReviewRepository {

    // *********************************************************
    // ReviewRepository
    // *********************************************************
    //region ReviewRepository

    override suspend fun addReview(review: MediaReview) {
        launchExternal {
            reviewDao.insertReview(review.toEntity())
        }
    }

    override suspend fun getAllReviews(): List<MediaReview> {
        return reviewDao.getAllReviews().map { it.toMediaReview() }
    }

    override suspend fun getAllReviewsWithMedia(): List<MediaReviewWithMedia> {
        val allReviews = reviewDao.getAllReviews()
        val relatedMedia: List<Media> = buildList {
            val reviewMediaUuids = allReviews.map { it.mediaId }.toSet()
            addAll(mediaDao.getMoviesByUuid(reviewMediaUuids).map { it.toMovie() })
            addAll(mediaDao.getTvShowsByUuid(reviewMediaUuids).map { it.toTvShow() })
            addAll(mediaDao.getTvSeasonsByUuid(reviewMediaUuids).map { it.toTvSeason() })
            addAll(mediaDao.getTvEpisodesByUuid(reviewMediaUuids).map { it.toTvEpisode() })
        }
        return allReviews.map { it.toMediaReview() }.map {
            MediaReviewWithMedia(
                value = it,
                media = relatedMedia.firstOrNull { media -> media.uuid == it.mediaUuid }!!
            )
        }
    }

    //endregion

    private suspend fun launchExternal(block: suspend () -> Unit) {
        externalScope.launch(coroutineDispatcher) {
            block()
        }.join()
    }
}
