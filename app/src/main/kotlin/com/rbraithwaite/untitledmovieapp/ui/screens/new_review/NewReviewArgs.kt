package com.rbraithwaite.untitledmovieapp.ui.screens.new_review

import android.os.Bundle
import androidx.navigation.NavBackStackEntry

sealed interface NewReviewArgs {
    data class NewMedia(val title: String): NewReviewArgs
}