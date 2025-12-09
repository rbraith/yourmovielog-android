package com.rbraithwaite.yourmovielog.data.repositories

import com.rbraithwaite.yourmovielog.test_utils.TestDependencyManager
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope

class ReviewRepositoryImplTests {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val testDependencyManager = TestDependencyManager(testScope, testDispatcher)

    private val reviewDao = testDependencyManager.reviewDao

    private val reviewRepository = ReviewRepositoryImpl(
        reviewDao,
        testScope,
        testDispatcher
    )
}