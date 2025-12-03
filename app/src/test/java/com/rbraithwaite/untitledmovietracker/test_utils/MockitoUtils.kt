package com.rbraithwaite.untitledmovietracker.test_utils

import org.mockito.kotlin.KArgumentCaptor


/**
 * Use this with argumentCaptor()
 */
fun <T> T.asVarArg(): Array<T> = this as Array<T>