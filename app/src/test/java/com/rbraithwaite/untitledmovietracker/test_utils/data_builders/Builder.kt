package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

// REFACTOR [23-10-21 10:14p.m.] -- this code is copied from task app.

interface Builder<T> {
    fun build(): T
}