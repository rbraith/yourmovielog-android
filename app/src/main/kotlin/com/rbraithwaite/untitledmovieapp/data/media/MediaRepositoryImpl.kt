package com.rbraithwaite.untitledmovieapp.data.media

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

class MediaRepositoryImpl(
    private val externalScope: CoroutineScope,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val mediaDao: MediaDao
): MediaRepository {
    override suspend fun addNewCustomMediaWithReview(customMedia: CustomMedia, review: MediaReview) {
        externalScope.launch {
            val customMediaEntity = CustomMediaEntity(
                title = customMedia.title
            )
            val mediaReviewEntity = MediaReviewEntity(
                mediaType = "custom",
                rating = review.rating,
                review = review.review,
                reviewDate = review.reviewDate,
                watchContext = review.watchContext
            )

            Timber.d("adding review to db: \n$customMediaEntity \n$mediaReviewEntity")

            mediaDao.addNewCustomMediaWithReview(
                customMediaEntity,
                mediaReviewEntity
            )
        }.join()
    }
}