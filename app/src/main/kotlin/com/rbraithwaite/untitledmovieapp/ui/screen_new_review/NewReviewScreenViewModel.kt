package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.lifecycle.ViewModel
import com.rbraithwaite.untitledmovieapp.ui.screen_new_review.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate

@HiltViewModel
class NewReviewScreenViewModel : ViewModel() {
    lateinit var initialMediaTitle: String

    // REFACTOR [23-09-27 11:01p.m.] -- need to inject a Now interface for testing.
    val initialReviewDate = ReviewDate(LocalDate.now())

    fun init(searchResult: NewReviewSearchResult?) {
        initialMediaTitle = when (searchResult) {
            is NewReviewSearchResult.NewCustomMedia -> searchResult.title
            null -> ""
        }
    }
}