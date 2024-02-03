package com.rbraithwaite.untitledmovietracker.test_utils.fakes

import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia
import com.rbraithwaite.untitledmovieapp.core.data.Review
import com.rbraithwaite.untitledmovieapp.core.data.TmdbLite
import com.rbraithwaite.untitledmovieapp.core.repositories.CustomMediaRepository
import com.rbraithwaite.untitledmovieapp.data.repositories.CustomMediaRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.mockito.kotlin.mock

// TODO [24-02-2 12:06a.m.] broken.
class DelegateFakeCustomMediaRepository(
    externalScope: CoroutineScope,
    coroutineDispatcher: CoroutineDispatcher,
    customMediaDao: FakeCustomMediaDao
): CustomMediaRepository {
    val mock: CustomMediaRepository = mock()

    private val real: CustomMediaRepository = CustomMediaRepositoryImpl(
        externalScope,
        coroutineDispatcher,
        customMediaDao
    )

    /**
     * Useful for setup code that you don't want to ruin late verifications.
     */
    var mockEnabled: Boolean = true

    suspend fun withMockEnabled(mockEnabled: Boolean, work: suspend CustomMediaRepository.() -> Unit) {
        this.mockEnabled = mockEnabled
        work()
        // BUG [23-11-19 4:38p.m.] -- this inversion is a bug, instead should cache old mockEnabled
        //  value and set back to that.
        this.mockEnabled = !mockEnabled
    }

    // *********************************************************
    // MediaRepository
    // *********************************************************
    //region MediaRepository
    override suspend fun addOrUpdateCustomMedia(vararg customMedia: CustomMedia) {
        TODO("Not yet implemented")
    }

    override suspend fun findMedia(searchCriteria: String): List<CustomMedia> {
        TODO("Not yet implemented")
    }

    //endregion
}