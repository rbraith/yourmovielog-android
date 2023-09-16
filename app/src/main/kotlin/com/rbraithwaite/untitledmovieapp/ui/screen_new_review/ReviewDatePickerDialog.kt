package com.rbraithwaite.untitledmovieapp.ui.screen_new_review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.YearMonth

data class ReviewDate(
    val year: Int,
    val month: Int?,
    val day: Int?
) {
    constructor(localDate: LocalDate) : this(
        year = localDate.year,
        // REFACTOR [23-09-14 10:48p.m.] -- should probably use 1-12 to align w/ java.time.
        month = localDate.monthValue - 1,
        day = localDate.dayOfMonth
    )
}

private enum class SelectorType {
    YEAR_SELECTOR,
    MONTH_SELECTOR,
    DAY_SELECTOR
}

@Composable
private fun rememberReviewDatePickerState(reviewDate: ReviewDate?): ReviewDatePickerState {
    return remember(reviewDate) {
        ReviewDatePickerState(reviewDate)
    }
}

@Stable
private class ReviewDatePickerState(
    inputReviewDate: ReviewDate?
) {
    companion object {
        const val UNSET_YEAR_TEXT = "Set\nYear"
        const val UNSET_MONTH_TEXT = "Set\nMonth"
        const val UNSET_DAY_TEXT = "Set\nDay"
    }

    var ratingDate by mutableStateOf(inputReviewDate)
        private set

    var selectorType by mutableStateOf(SelectorType.YEAR_SELECTOR)

    val selectedYear by derivedStateOf {
        ratingDate?.year ?: YEAR_NONE
    }
    val selectedYearText by derivedStateOf {
        ratingDate?.year?.toString() ?: UNSET_YEAR_TEXT
    }

    val selectedMonth by derivedStateOf {
        ratingDate?.month ?: MONTH_NONE
    }
    val selectedMonthText by derivedStateOf {
        ratingDate?.month?.let { MONTHS[it] } ?: UNSET_MONTH_TEXT
    }

    val selectedDay by derivedStateOf {
        ratingDate?.day ?: DAY_NONE
    }
    val selectedDayText by derivedStateOf {
        ratingDate?.day?.toString() ?: UNSET_DAY_TEXT
    }

    fun updateYear(year: Int) {
        ratingDate = if (year == YEAR_NONE) {
            null
        } else {
            ReviewDate(
                year,
                if (selectedMonth == MONTH_NONE) null else selectedMonth,
                // clearing the date since changing the year can affect days of the month in feb
                // TODO [23-09-10 7:12p.m.] -- maybe I don't need to clear this?
                null
            )
        }
    }

    fun updateMonth(month: Int) {
        if (month != selectedMonth) {
            ratingDate = ReviewDate(
                selectedYear,
                if (month == MONTH_NONE) null else month,
                null
            )
        }
    }

    fun updateDay(day: Int) {
        ratingDate = ReviewDate(
            selectedYear,
            selectedMonth,
            if (day == DAY_NONE) null else day
        )
    }

    val yearMonth: YearMonth
        get() = YearMonth.of(
            selectedYear,
            selectedMonth + 1
        )
}

@Composable
fun ReviewDatePickerDialog(
    initialReviewDate: ReviewDate?,
    onDismiss: () -> Unit,
    onConfirm: (ReviewDate?) -> Unit
) {
    val state = rememberReviewDatePickerState(reviewDate = initialReviewDate)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { onConfirm(state.ratingDate) }) {
                Text("Confirm")
            }
        },
        text = {
            ReviewDatePickerContent(state)
        }
    )
}

@Preview
@Composable
fun PreviewReviewDatePickerDialog() {
    ReviewDatePickerDialog(
        initialReviewDate = null,
        onDismiss = { /*TODO*/ },
        onConfirm = {}
    )
}
@Composable
private fun ReviewDatePickerContent(
    state: ReviewDatePickerState
) {
    Column {
        TopRowButtons(
            yearText = state.selectedYearText,
            monthText = state.selectedMonthText,
            dayText = state.selectedDayText,
            onChangeSelector = { when (it) {
                SelectorType.YEAR_SELECTOR -> {
                    state.selectorType = it
                }
                SelectorType.MONTH_SELECTOR -> {
                    if (state.selectedYear != YEAR_NONE) {
                        state.selectorType = it
                    }
                }
                SelectorType.DAY_SELECTOR -> {
                    if (state.selectedMonth != MONTH_NONE) {
                        state.selectorType = it
                    }
                }
            } }
        )

        when (state.selectorType) {
            SelectorType.YEAR_SELECTOR -> {
                YearSelector(
                    initialSelectedYear = state.selectedYear,
                    onYearSelected = { newYear ->
                        state.updateYear(newYear)
                    }
                )
            }
            SelectorType.MONTH_SELECTOR -> {
                MonthSelector(
                    selectedMonth = state.selectedMonth,
                    onSelectMonth = { newMonth ->
                        state.updateMonth(newMonth)
                    }
                )
            }
            SelectorType.DAY_SELECTOR -> {
                DaySelector(
                    selectedDay = state.selectedDay,
                    month = state.yearMonth,
                    onSelectDay = { newDay ->
                        state.updateDay(newDay)
                    }
                )
            }
        }
    }
}

@Composable
private fun TopRowButtons(
    yearText: String,
    monthText: String,
    dayText: String,
    onChangeSelector: (SelectorType) -> Unit
) {
    Row {
        Button(onClick = {
            onChangeSelector(SelectorType.YEAR_SELECTOR)
        }) {
            Text(yearText)
        }
        Button(onClick = {
            onChangeSelector(SelectorType.MONTH_SELECTOR)
        }) {
            Text(monthText)
        }
        Button(onClick = {
            onChangeSelector(SelectorType.DAY_SELECTOR)
        }) {
            Text(dayText)
        }
    }
}

const val YEAR_NONE = -1

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun YearSelector(
    initialSelectedYear: Int,
    onYearSelected: (Int) -> Unit,
) {
    var selectedYear by remember(initialSelectedYear) { mutableIntStateOf(initialSelectedYear) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        TextField(
            value = if (selectedYear == YEAR_NONE) "" else selectedYear.toString(),
            onValueChange = {
                selectedYear = it.toIntOrNull() ?: selectedYear
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                onYearSelected(selectedYear)
            })
        )
        TextButton(onClick = {
            onYearSelected(YEAR_NONE)
        }) {
            Text("clear year")
        }
    }
}

const val MONTH_NONE = -1

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun MonthSelector(
    selectedMonth: Int,
    onSelectMonth: (Int) -> Unit
) {
    FlowRow {
        FilterChip(
            selected = selectedMonth == MONTH_NONE,
            onClick = { onSelectMonth(MONTH_NONE) },
            label = { Text("NONE")}
        )

        for ((i, month) in MONTHS.withIndex()) {
            FilterChip(
                selected = i == selectedMonth,
                onClick = { onSelectMonth(i) },
                label = { Text(month) }
            )
        }
    }
}

const val DAY_NONE = -1

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun DaySelector(
    // REFACTOR [23-09-10 4:11p.m.] -- YearMonth probably isn't stable.
    selectedDay: Int,
    month: YearMonth,
    onSelectDay: (Int) -> Unit
) {
    val numberOfDays = remember(month) { month.lengthOfMonth() }

    FlowRow {
        FilterChip(
            selected = selectedDay == DAY_NONE,
            onClick = { onSelectDay(DAY_NONE) },
            label = { Text("NONE") }
        )

        for (i in 1..numberOfDays) {
            FilterChip(
                selected = i == selectedDay,
                onClick = { onSelectDay(i) },
                label = { Text((i).toString()) }
            )
        }
    }
}

private val MONTHS = listOf(
    "jan",
    "feb",
    "mar",
    "apr",
    "may",
    "jun",
    "jul",
    "aug",
    "sep",
    "oct",
    "nov",
    "dec"
)