package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.core.data.Review

@BaseBuilder
abstract class AbstractReviewBuilder: TestDataBuilder<Review>()

class ReviewBuilder: BaseAbstractReviewBuilder<ReviewBuilder>() {
    override var data = Review(
        mediaType = Review.MediaType.CustomMovie(id = 1L)
    )
}

fun aReview(buildBlock: ReviewBuilder.() -> Unit = {}): ReviewBuilder {
    return ReviewBuilder().apply(buildBlock)
}