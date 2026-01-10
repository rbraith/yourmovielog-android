package com.rbraithwaite.yourmovielog.ui.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// REFACTOR [23-08-29 9:02p.m.] -- this should be extracted to a library.
@Composable
fun DebugPlaceholder(
    modifier: Modifier = Modifier,
    label: String = "WIP",
    color: Color? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(color ?: randomColor())
    ) {
        Text(label)
    }
}