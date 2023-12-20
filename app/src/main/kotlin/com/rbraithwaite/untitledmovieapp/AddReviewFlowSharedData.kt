package com.rbraithwaite.untitledmovieapp

import androidx.lifecycle.ViewModel
import com.rbraithwaite.untitledmovieapp.core.data.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Data shared between destinations of the "Add Review" navigation flow
 */
@HiltViewModel
class AddReviewFlowSharedData @Inject constructor(): ViewModel() {
    var newReviewSearchResult: SearchResult? = null
}