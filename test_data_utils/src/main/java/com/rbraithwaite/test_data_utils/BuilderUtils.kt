package com.rbraithwaite.test_data_utils

fun <T> valueOf(builder: Builder<T>): T {
    return builder.build()
}

fun <T> listValuesOf(vararg builders: Builder<T>): List<T> {
    return builders.map { valueOf(it) }
}

fun <T> listValuesOf(builders: List<Builder<T>>): List<T> {
    return builders.map { valueOf(it) }
}

inline fun <reified T> arrayValuesOf(vararg builders: Builder<T>): Array<T> {
    return listValuesOf(*builders).toTypedArray()
}