package com.rbraithwaite.yourmovielog

import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

object DebugUtils {
    // REFACTOR [23-12-16 11:40p.m.] -- this duplicates MockResponse.setBodyFromResourceFile() from test set.
    /**
     * @param resourceFilepath This path should be relative to the resources/ directory
     */
    fun loadResourceFileAsString(resourceFilepath: String): String {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(resourceFilepath)
        val source = inputStream.source().buffer()
        return source.readString(StandardCharsets.UTF_8)
    }
}