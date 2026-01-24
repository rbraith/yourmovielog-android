package com.rbraithwaite.yourmovielog.ui.screens.new_review.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.rbraithwaite.yourmovielog.core.data.Media
import com.rbraithwaite.yourmovielog.core.data.Movie
import com.rbraithwaite.yourmovielog.core.data.TvShow
import kotlin.reflect.KClass

private fun isTv(mediaType: KClass<out Media>): Boolean {
    return mediaType != Movie::class
}

@Composable
fun MediaTypeSelector(
    selectedMediaType: KClass<out Media>,
    onSelectMediaType: (KClass<out Media>) -> Unit
) {
    TabRow(
        selectedTabIndex = if (selectedMediaType == Movie::class) 0 else 1
    ) {
        Tab(
            text = { Text("movie") },
            selected = selectedMediaType == Movie::class,
            onClick = { onSelectMediaType(Movie::class) }
        )
        Tab(
            text = { Text("tv show") },
            selected = isTv(selectedMediaType),
            onClick = { onSelectMediaType(TvShow::class) }
        )
    }

    if (isTv(selectedMediaType)) {
        TabRow(
            selectedTabIndex = when (selectedMediaType) {
                TvShow::class -> 0
                TvShow.Season::class -> 1
                TvShow.Episode::class -> 2
                else -> 0
            }
        ) {
            Tab(
                text = { Text("show") },
                selected = selectedMediaType == TvShow::class,
                onClick = { onSelectMediaType(TvShow::class) }
            )
            Tab(
                text = { Text("season") },
                selected = selectedMediaType == TvShow.Season::class,
                onClick = { onSelectMediaType(TvShow.Season::class) }
            )
            Tab(
                text = { Text("episode") },
                selected = selectedMediaType == TvShow.Episode::class,
                onClick = { onSelectMediaType(TvShow.Episode::class) }
            )
        }
    }
}
