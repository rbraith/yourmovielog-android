package com.rbraithwaite.untitledmovietracker.test_utils.base_tests

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

// IN PROGRESS [23-12-2 12:43a.m.] this will be the base test class for api testing.
abstract class RetrofitApiTests {
    lateinit var mockWebServer: MockWebServer

    @Before
    open fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    open fun teardown() {
        mockWebServer.shutdown()
    }
}