package com.rbraithwaite.yourmovielog.test_utils

/**
 * Use this with argumentCaptor()
 */
fun <T> T.asVarArg(): Array<T> = this as Array<T>
