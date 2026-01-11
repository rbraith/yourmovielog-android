package com.rbraithwaite.yourmovielog.test_utils

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher

// REFACTOR [23-10-12 9:53p.m.] -- these are from task app, move them to external test utils lib.

/**
 * Since `is`(equalTo()) is ugly :/
 */
fun <T> willBeEqualTo(operand: T): Matcher<T> {
    return equalTo(operand)
}

/**
 * Instead of `is`()
 */
fun <T> willBe(operand: T): Matcher<T> {
    return `is`(operand)
}
