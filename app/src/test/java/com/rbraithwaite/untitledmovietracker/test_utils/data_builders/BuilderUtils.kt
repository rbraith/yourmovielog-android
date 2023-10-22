package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

// REFACTOR [23-10-21 10:14p.m.] -- this code is copied from task app.

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