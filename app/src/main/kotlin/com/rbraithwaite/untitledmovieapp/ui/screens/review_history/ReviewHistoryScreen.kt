package com.rbraithwaite.untitledmovieapp.ui.screens.review_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor

@Composable
fun ReviewHistoryScreen() {
    val testData = remember {
        listOf(
            "what",
            "the",
            "heck"
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().randomBackgroundColor()
    ) {
        items(testData, key = { it }) {
            DebugPlaceholder(
                label = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )
        }
    }
}