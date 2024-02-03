package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.core.data.Review

@BaseBuilder
abstract class AbstractMediaReviewBuilder: TestDataBuilder<Review>()

// TODO [24-02-2 12:05a.m.] broken.
//fun mediaReview(block: (MediaReviewBuilder.() -> Unit)? = null): MediaReviewBuilder {
//    return MediaReviewBuilder().also { builder -> block?.let { builder.apply(it) } }
//}
//
//class MediaReviewBuilder: BaseAbstractMediaReviewBuilder<MediaReviewBuilder>() {
//    override var data: Review = Review()
//}