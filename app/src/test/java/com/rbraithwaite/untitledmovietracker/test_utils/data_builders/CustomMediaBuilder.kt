package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

import com.rbraithwaite.test_data_utils.Builder

// TODO [24-02-2 12:05a.m.] broken.
//fun aCustomMedia(buildBlock: CustomMediaBuilder.() -> Unit = {}): CustomMediaBuilder {
//    return CustomMediaBuilder().apply(buildBlock)
//}
//
//class CustomMediaBuilder: Builder<CustomMovie> {
//    private var data = CustomMovie(
//        id = 0L,
//        title = "test custom media"
//    )
//
//    override fun build(): CustomMovie {
//        return data.copy()
//    }
//
//    fun withTitle(title: String) = apply {
//        data = data.copy(title = title)
//    }
//
//    fun withId(id: Long) = apply {
//        data = data.copy(id = id)
//    }
//}