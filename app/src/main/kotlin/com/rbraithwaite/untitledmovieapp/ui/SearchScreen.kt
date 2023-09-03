package com.rbraithwaite.untitledmovieapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rbraithwaite.untitledmovieapp.ui.debug.DebugPlaceholder
import com.rbraithwaite.untitledmovieapp.ui.debug.randomBackgroundColor
import timber.log.Timber

@Composable
fun SearchScreen() {
    var quickSearchInput by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .randomBackgroundColor()
    ) {
        item {
            QuickSearchWidget(
                onSearchInputChange = { quickSearchInput = it },
                onGoToAdvancedSearch = {
                    // TO IMPLEMENT
                    Timber.d("QuickSearchWidget onSearchInputChange")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Divider(color = Color.Red)
        }

        SearchResults(quickSearchInput = quickSearchInput)
    }
}

@Composable
fun QuickSearchWidget(
    onSearchInputChange: (String) -> Unit,
    onGoToAdvancedSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        var searchInput by rememberSaveable {
            mutableStateOf("")
        }

        Text(
            text = "Quick Search",
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        TextField(
            value = searchInput,
            onValueChange = {
                searchInput = it
                onSearchInputChange(it)
            },
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = onGoToAdvancedSearch,
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 8.dp, end = 8.dp)
        ) {
            Text(text = "Advanced Search")
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}

fun LazyListScope.SearchResults(quickSearchInput: String) {

}

@Preview
@Composable
fun PreviewSearchScreen() {
    SearchScreen()
}