package com.rbraithwaite.untitledmovietracker.test_utils.base_tests

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

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