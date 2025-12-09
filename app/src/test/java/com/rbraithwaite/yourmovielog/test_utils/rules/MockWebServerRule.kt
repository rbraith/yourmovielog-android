package com.rbraithwaite.yourmovielog.test_utils.rules

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockWebServerRule: TestWatcher() {
    val server = MockWebServer()

    override fun starting(description: Description) {
        server.start()
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}