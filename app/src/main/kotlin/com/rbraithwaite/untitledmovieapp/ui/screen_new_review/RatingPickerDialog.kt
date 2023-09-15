package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

/**
 * @param onConfirm Returns selected rating as 0-100 Int, or null if rating is unselected
 */
@Composable
fun RatingPickerDialog(
    initialRating: Int? = null,
    onDismiss: () -> Unit,
    onConfirm: (Int?) -> Unit
) {
    var ratingString by remember(initialRating) { mutableStateOf(formatRating(initialRating)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Row {
                TextField(
                    value = ratingString,
                    onValueChange = {
                        if (isValidRatingString(it)) {
                            ratingString = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "/ 10",
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(parseRatingString(ratingString))
            }) {
                Text("Confirm")
            }
        }
    )
}

// the rating string arrives as a float value between 0-10, converted to int 0-100
private fun parseRatingString(ratingString: String): Int? {
    if (ratingString.isBlank()) {
        return null
    }

    if (ratingString.endsWith(".")) {
        return ratingString.dropLast(1).toIntOrNull()?.let { it * 10 }
    }

    if (ratingString.startsWith(".")) {
        // return first digit after the decimal point
        return ratingString.drop(1).getOrNull(0)?.digitToIntOrNull()
    }

    if (!ratingString.contains(".")) {
        return ratingString.toIntOrNull()?.let { it * 10 }
    }

    // last case where there are digits on both sides of the decimal point
    val decimalSplit = ratingString.split(".")
    val beforeDec = decimalSplit.getOrNull(0)?.toIntOrNull()?.let { it * 10 }
    // only first digit after decimal
    val afterDec = decimalSplit.getOrNull(1)?.getOrNull(0)?.digitToIntOrNull()

    if (beforeDec == null || afterDec == null) {
        return null
    }

    return beforeDec + afterDec
}

private fun isValidRatingString(ratingString: String): Boolean  {
    if (ratingString.matches(Regex("^10\\.?0?\$"))) {
        // matches for 10, 10., 10.0
        return true
    }

    if (ratingString.matches(Regex("^[0-9]?\\.?[0-9]?\$"))) {
        // matches for values less than 10, such as .5, 8.2, etc
        // limits to 1 decimal place
        return true
    }

    return false
}

// REFACTOR [23-09-5 11:54p.m.] -- duplicated in NewReviewScreen.
private fun formatRating(rating: Int?): String {
    return if (rating == null) {
        ""
    } else {
        val beforeDecimal = rating / 10
        val afterDecimal = rating % 10

        if (afterDecimal == 0) {
            beforeDecimal.toString()
        } else {
            "$beforeDecimal.$afterDecimal"
        }
    }
}

@Preview
@Composable
fun PreviewRatingPickerDialog() {
    RatingPickerDialog(
        onDismiss = {},
        onConfirm = {}
    )
}