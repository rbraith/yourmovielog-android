package com.rbraithwaite.untitledmovietracker.test_utils.data_builders

import com.rbraithwaite.test_data_utils.Builder
import com.rbraithwaite.untitledmovieapp.core.data.CustomMedia

fun aCustomMedia(buildBlock: CustomMediaBuilder.() -> Unit = {}): CustomMediaBuilder {
    return CustomMediaBuilder().apply(buildBlock)
}

class CustomMediaBuilder: Builder<CustomMedia> {
    private var data = CustomMedia(
        id = 0L,
        title = "test custom media"
    )

    override fun build(): CustomMedia {
        return data.copy()
    }

    fun withTitle(title: String) = apply {
        data = data.copy(title = title)
    }

    fun withId(id: Long) = apply {
        data = data.copy(id = id)
    }
}