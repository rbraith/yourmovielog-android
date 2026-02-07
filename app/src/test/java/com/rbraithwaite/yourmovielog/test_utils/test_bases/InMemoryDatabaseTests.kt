package com.rbraithwaite.yourmovielog.test_utils.test_bases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.rbraithwaite.yourmovielog.data.database.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
abstract class InMemoryDatabaseTests {
    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    protected lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()

        onDatabaseCreated()
    }

    @After
    fun tearDown() {
        database.close()
    }

    protected open fun onDatabaseCreated() {}
}
