package com.rbraithwaite.untitledmovieapp.ui.debug

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// REFACTOR [23-08-29 9:04p.m.] -- extract this file to a library.

fun Modifier.debugBackgroundColor(): Modifier {
    return this.background(randomColor())
}

fun Modifier.debugBorder(size: Dp = 2.dp, color: Color? = null): Modifier {
    return this.border(BorderStroke(size, color ?: randomColor()))
}

// SMELL [23-02-11 7:10p.m.] -- There's probably a build in api for this lol idk.
fun randomColor(): Color {
    return Color(
        (0..255).random(),
        (0..255).random(),
        (0..255).random(),
    )
}