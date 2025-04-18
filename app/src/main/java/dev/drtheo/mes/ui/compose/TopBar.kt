package dev.drtheo.mes.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.drtheo.mes.BuiltInDnevnikScreen
import dev.drtheo.mes.CustomDnevnikScreen
import dev.drtheo.mes.DnevnikScreen
import dev.drtheo.mes.R
import dev.drtheo.mes.Screens
import dev.drtheo.mes.same
import dev.drtheo.mes.ui.DnevnikViewModel
import dev.drtheo.mes.ui.theme.DnevnikTheme
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnevnikTopAppBar(
    viewModel: DnevnikViewModel,
    currentScreen: DnevnikScreen,
    scrollBehavior: TopAppBarScrollBehavior,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopBarContainer(
        currentScreen = currentScreen,
        scrollBehavior = scrollBehavior,
        canNavigateBack = canNavigateBack,
        navigateUp = navigateUp,
        setDate = { date ->
            viewModel.date(date)
        },
        currentDate = viewModel.date,
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBarContainer(
    currentScreen: DnevnikScreen,
    scrollBehavior: TopAppBarScrollBehavior,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    setDate: (Calendar) -> Unit,
    currentDate: Calendar,
    modifier: Modifier = Modifier,
) {
    val title = when (currentScreen) {
        is BuiltInDnevnikScreen -> stringResource(currentScreen.titleRes)
        is CustomDnevnikScreen -> currentScreen.title
    }

    val hasCalendar = currentScreen.hasCalendar

    Column {
        CenterAlignedTopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                )
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            },
            modifier = modifier
        )

        if (hasCalendar) {
            SwipeableWeekDays(
                selectedDate = currentDate,
                onDateSelected = setDate,
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun DnevnikTopAppBarPreview() {
    var selectedDate by remember { mutableStateOf<Calendar>(Calendar.getInstance()) }

    DnevnikTheme {
        TopBarContainer(
            currentScreen = Screens.Schedule,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            canNavigateBack = false,
            navigateUp = { },
            setDate = { date ->
                selectedDate = date
            },
            currentDate = selectedDate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
private fun SwipeableWeekDays(
    onDateSelected: (Calendar) -> Unit,
    selectedDate: Calendar,
    modifier: Modifier = Modifier
) {
    var weekOffset by remember { mutableIntStateOf(0) }
    val currentWeek = remember(weekOffset) {
        getMondayFirstWeek(weekOffset)
    }

    var dragOffset by remember { mutableFloatStateOf(0F) }
    var isDragging by remember { mutableStateOf(false) }

    val days = remember(currentWeek) { getMondayBasedWeekDays(currentWeek) }
    var shouldDisplayModal by remember { mutableStateOf(false) }

    if (shouldDisplayModal) {
        DatePickerModal(
            onDateSelected = { time ->
                shouldDisplayModal = false

                if (time == null)
                    return@DatePickerModal

                val date = Calendar.getInstance().apply {
                    timeInMillis = time
                }

                onDateSelected(date)
            },
            onDismiss = {
                shouldDisplayModal = false
            }
        )
    }

    LaunchedEffect(isDragging) {
        if (!isDragging && abs(dragOffset) > 20f) {
            weekOffset += if (dragOffset > 0) -1 else 1
            dragOffset = 0F
        }
    }

    Column(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { isDragging = true },
                    onDragEnd = { isDragging = false },
                    onDrag = { _, dragAmount ->
                        dragOffset += dragAmount.x

                        if (dragAmount.y > 50 && dragAmount.x < 10)
                            shouldDisplayModal = true
                    }
                )
            }
    ) {
        WeekDaysRow(
            modifier = Modifier.fillMaxWidth().offset { IntOffset(dragOffset.roundToInt(), 0) },
            days = days,
            isDragInProgress = isDragging,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
        )
    }
}

@Composable
private fun WeekDaysRow(
    modifier: Modifier = Modifier,
    days: List<Calendar>,
    isDragInProgress: Boolean,
    selectedDate: Calendar,
    onDateSelected: (Calendar) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        days.forEach { day ->
            DayItem(
                day = day,
                isToday = day.same(Calendar.getInstance()),
                isSelected = day.same(selectedDate),
                enabled = !isDragInProgress,
                onClick = { onDateSelected(day) }
            )
        }
    }
}

@Composable
private fun DayItem(
    day: Calendar,
    isToday: Boolean,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dayName = day.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
    val dayNumber = day.get(Calendar.DAY_OF_MONTH).toString()

    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainer
    val weight = if (isToday) FontWeight.Bold else FontWeight.Normal

    val textColor = if (isToday || isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Column(
        modifier = modifier.padding(
            bottom = 8.dp, top = 0.dp,
            start = 8.dp, end = 8.dp,
        ).clickable(
            enabled = enabled,
            onClick = onClick
        ).background(backgroundColor, CircleShape)
            .defaultMinSize(minWidth = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = dayName ?: "",
            color = textColor,
            fontWeight = weight,
            fontSize = 14.sp
        )
        Text(
            text = dayNumber,
            color = textColor,
            fontWeight = weight,
            fontSize = 16.sp
        )
    }
}

private fun getMondayFirstWeek(weekOffset: Int): Calendar {
    return Calendar.getInstance().apply {
        while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            add(Calendar.DAY_OF_MONTH, -1)
        }

        add(Calendar.WEEK_OF_YEAR, weekOffset)
    }
}

private fun getMondayBasedWeekDays(startCalendar: Calendar): List<Calendar> {
    return List(7) { offset ->
        (startCalendar.clone() as Calendar).apply {
            add(Calendar.DAY_OF_MONTH, offset)
        }
    }
}