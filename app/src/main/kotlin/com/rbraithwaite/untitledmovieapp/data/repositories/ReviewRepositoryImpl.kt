package com.rbraithwaite.untitledmovieapp.data.repositories

import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.repositories.ReviewRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.conversions.toEntity
import com.rbraithwaite.untitledmovieapp.data.database.dao.ReviewDao
import com.rbraithwaite.untitledmovieapp.di.SingletonModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.reflect.KClass

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