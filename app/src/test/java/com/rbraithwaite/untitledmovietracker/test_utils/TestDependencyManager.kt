package com.rbraithwaite.untitledmovietracker.test_utils

import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.CustomMediaBuilder
import com.rbraithwaite.untitledmovietracker.test_utils.data_builders.valueOf
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.DelegateFakeMediaRepository
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeDatabase
import com.rbraithwaite.untitledmovietracker.test_utils.fakes.FakeMediaDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

/**
 * Responsible for connecting test dependencies and initializing those dependencies.
 */
class TestDependencyManager(
    private val externalScope: CoroutineScope,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val localDatabase: FakeDatabase by lazy {
        FakeDatabase()
    }

    private val mediaDao: FakeMediaDao by lazy {
        FakeMediaDao(localDatabase)
    }

    val mediaRepository: DelegateFakeMediaRepository by lazy {
        DelegateFakeMediaRepository(
            externalScope,
            coroutineDispatcher,
            mediaDao
        )
    }

    // *********************************************************
    // Test State Initialization
    // *********************************************************
    //region Test State Initialization

    interface TestStateInitializer {
        /**
         * @param customMedia The custom media to initialize the repo with
         */
        suspend fun withCustomMedia(vararg customMedia: CustomMediaBuilder)
    }

    private inner class TestStateInitializerImpl: TestStateInitializer {
        override suspend fun withCustomMedia(vararg customMedia: CustomMediaBuilder) {
            customMedia.forEach {
                this@TestDependencyManager.mediaRepository.withMockEnabled(false) {
                    addNewCustomMedia(valueOf(it))
                }
            }
        }
    }

    suspend fun initializeTestState(initialize: suspend TestStateInitializer.() -> Unit) {
        TestStateInitializerImpl().initialize()
    }

    //endregion
}