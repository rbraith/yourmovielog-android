package com.rbraithwaite.untitledmovietracker.data.repositories

import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity
import com.rbraithwaite.untitledmovieapp.data.repositories.ReviewRepositoryImpl
import com.rbraithwaite.untitledmovietracker.test_utils.TestDependencyManager
import com.rbraithwaite.untitledmovietracker.test_utils.asVarArg
import com.rbraithwaite.untitledmovietracker.test_utils.willBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify

class ReviewRepositoryImplTests {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val testDependencyManager = TestDependencyManager(testScope, testDispatcher)

    private val reviewDao = testDependencyManager.reviewDao
    private val tmdbDao = testDependencyManager.tmdbDao

    private val reviewRepository = ReviewRepositoryImpl(
        reviewDao,
        tmdbDao,
        testScope,
        testDispatcher
    )
}