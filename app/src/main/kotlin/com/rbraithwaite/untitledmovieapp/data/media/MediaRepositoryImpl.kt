package com.rbraithwaite.untitledmovieapp.data.media

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Media
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

    override suspend fun addNewCustomMedia(customMedia: CustomMedia) {
        externalScope.launch(coroutineDispatcher) {
            mediaDao.addCustomMedia(customMedia.toNewEntity())
        }.join()
    }

    override suspend fun addNewCustomMediaWithReview(customMedia: CustomMedia, review: MediaReview) {
        externalScope.launch(coroutineDispatcher) {
            val customMediaEntity = customMedia.toNewEntity()
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

    override suspend fun findMedia(searchCriteria: String): List<Media> {
        val foundCustomMedia = mediaDao.searchCustomMedia(searchCriteria)
        return foundCustomMedia.map { it.toCustomMedia() }
    }

    private fun CustomMedia.toNewEntity(): CustomMediaEntity {
        return CustomMediaEntity(title = this.title)
    }

    private fun CustomMediaEntity.toCustomMedia(): CustomMedia {
        return CustomMedia(id, title)
    }
}