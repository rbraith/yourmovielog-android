package com.rbraithwaite.yourmovielog.ui.screens.new_review

sealed interface NewReviewArgs {
    data class NewMedia(val title: String): NewReviewArgs
}