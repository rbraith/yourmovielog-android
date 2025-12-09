package com.rbraithwaite.yourmovielog.data.repositories

import com.rbraithwaite.yourmovielog.core.data.MediaReview
import com.rbraithwaite.yourmovielog.core.repositories.ReviewRepository
import com.rbraithwaite.yourmovielog.data.repositories.conversions.toEntity
import com.rbraithwaite.yourmovielog.data.database.dao.ReviewDao
import com.rbraithwaite.yourmovielog.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDao: ReviewDao,
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
}