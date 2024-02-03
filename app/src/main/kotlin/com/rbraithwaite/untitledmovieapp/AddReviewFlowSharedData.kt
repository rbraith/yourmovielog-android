package com.rbraithwaite.untitledmovieapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Data shared between destinations of the "Add Review" navigation flow
 */
@HiltViewModel
class AddReviewFlowSharedData @Inject constructor(): ViewModel() {
    // TODO [24-02-2 12:13a.m.] broken.
//    var newReviewSearchResult: SearchResult? = null
}