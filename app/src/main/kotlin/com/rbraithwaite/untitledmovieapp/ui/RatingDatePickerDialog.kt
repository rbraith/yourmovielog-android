package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RatingDatePickerDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /*TODO*/ },
        text = {
            Text("date picker!!!")
        }
    )
}