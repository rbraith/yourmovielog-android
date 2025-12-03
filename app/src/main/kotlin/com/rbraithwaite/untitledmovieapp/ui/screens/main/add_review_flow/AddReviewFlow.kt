package com.rbraithwaite.untitledmovieapp.ui.screens.main.add_review_flow

import androidx.lifecycle.ViewModel
import com.rbraithwaite.untitledmovieapp.ui.screens.new_review.NewReviewArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class AddReviewFlowDest(val route: String) {
    SEARCH("search"),
    ADD_REVIEW("add-review")
}

/**
 * Data shared between destinations of the "Add Review" navigation flow
 */
@HiltViewModel
class AddReviewFlowSharedData @Inject constructor(): ViewModel() {
    var newReviewArgs: NewReviewArgs? = null
}