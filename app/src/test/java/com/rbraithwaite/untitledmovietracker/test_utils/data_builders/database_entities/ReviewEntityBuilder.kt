package com.rbraithwaite.untitledmovietracker.test_utils.data_builders.database_entities

import com.rbraithwaite.test_data_utils.TestDataBuilder
import com.rbraithwaite.test_data_utils.builder_base.BaseBuilder
import com.rbraithwaite.untitledmovieapp.data.database.entities.ReviewEntity

@BaseBuilder
abstract class AbstractReviewEntityBuilder: TestDataBuilder<ReviewEntity>()

class ReviewEntityBuilder: BaseAbstractReviewEntityBuilder<ReviewEntityBuilder>() {
    override var data = ReviewEntity(
        mediaId = 1L,
        mediaType = ReviewEntity.Type.CUSTOM.value,
        rating = null,
        review = null,
        reviewDate = null,
        watchContext = null
    )

    fun withRelatedMedia(id: Long, type: ReviewEntity.Type): ReviewEntityBuilder {
        return this.withMediaId(id).withMediaType(type.value)
    }
}

fun aReviewEntity(buildBlock: ReviewEntityBuilder.() -> Unit = {}): ReviewEntityBuilder {
    return ReviewEntityBuilder().apply(buildBlock)
}