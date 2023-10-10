package com.rbraithwaite.untitledmovieapp.data.media

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.data.database.CustomMediaEntity
import com.rbraithwaite.untitledmovieapp.data.database.MediaDao
import com.rbraithwaite.untitledmovieapp.data.database.MediaReviewEntity
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MediaRepositoryImpl(
    private val externalScope: CoroutineScope,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val mediaDao: MediaDao
): MediaRepository {
    override suspend fun addNewCustomMediaWithReview(
        customMediaTitle: String,
        review: MediaReview
    ): Result<Unit> {
        return externalScope.async(coroutineDispatcher) {
            val customMediaEntity = CustomMediaEntity(title = customMediaTitle)
            val mediaReviewEntity = MediaReviewEntity(
                mediaType = "custom",
                rating = review.rating,
                review = review.review,
                reviewDate = review.reviewDate,
                watchContext = review.watchContext
            )

            Timber.d("adding review to db: \n$customMediaEntity \n$mediaReviewEntity")

            mediaDao.addCustomMediaWithReview(
                customMediaEntity,
                mediaReviewEntity
            )

            Result.success(Unit)
        }.await()
    }
}