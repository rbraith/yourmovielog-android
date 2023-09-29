package com.rbraithwaite.untitledmovieapp.ui.screen_new_review.data

/**
 * A review of a "custom media". Custom media is user-defined instead of backend-defined.
 */
data class CustomMediaReview(
    val mediaName: String,
    val review: MediaReview
)
