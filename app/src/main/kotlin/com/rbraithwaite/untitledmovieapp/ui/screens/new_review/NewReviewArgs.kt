package com.rbraithwaite.untitledmovieapp.ui.screens.new_review

import android.os.Bundle
import androidx.navigation.NavBackStackEntry

abstract class NewReviewArgs {
    abstract val typeKey: String
    abstract val value: String

    fun asRouteString(): String {
        return "$typeKey/$value"
    }

    companion object {
        const val TYPE_NEW_MEDIA = "new-media"

        fun fromBackStackEntry(backStackEntry: NavBackStackEntry): NewReviewArgs? {
            val arguments = backStackEntry.arguments ?: return null

            val typeKey = arguments.getString("arg-type") ?: return null
            val value = arguments.getString("arg-value") ?: return null

            return when (typeKey) {
                TYPE_NEW_MEDIA -> NewMedia(value)
                else -> null
            }

        }
    }

    class NewMedia(title: String): NewReviewArgs() {
        override val value = title
        override val typeKey = TYPE_NEW_MEDIA
    }
}