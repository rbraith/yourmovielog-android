package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbraithwaite.untitledmovieapp.core.repositories.MediaRepository
import com.rbraithwaite.untitledmovieapp.core.data.MediaReview
import com.rbraithwaite.untitledmovieapp.core.data.ReviewDate
import com.rbraithwaite.untitledmovieapp.ui.screen_search.NewReviewSearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NewReviewScreenViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {
    lateinit var initialMediaTitle: String

    // REFACTOR [23-09-27 11:01p.m.] -- need to inject a Now interface for testing.
    val initialReviewDate = ReviewDate(LocalDate.now())

    // TODO [23-09-29 12:38a.m.] -- why is searchResult nullable again?
    private var searchResult: NewReviewSearchResult? = null

    fun init(searchResult: NewReviewSearchResult?) {
        this.searchResult = searchResult
        initialMediaTitle = when (searchResult) {
            is NewReviewSearchResult.NewCustomMedia -> searchResult.title
            null -> ""
        }
    }

    fun confirmReview(review: MediaReview) {
        when (val searchResultVal = searchResult) {
            is NewReviewSearchResult.NewCustomMedia -> {
                viewModelScope.launch {
                    mediaRepository.addNewCustomMediaWithReview(
                        searchResultVal.title,
                        review
                    )
                }
            }
            null -> {}
        }
    }
}