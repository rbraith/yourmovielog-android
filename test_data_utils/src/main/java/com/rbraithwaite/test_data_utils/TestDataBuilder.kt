package com.rbraithwaite.test_data_utils

abstract class TestDataBuilder<T>: Builder<T> {
    protected abstract var data: T
}