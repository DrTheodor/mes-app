package dev.drtheo.mes.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.drtheo.mes.ErrorScreen
import dev.drtheo.mes.LoadingScreen
import dev.drtheo.mes.formatHomework
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.model.event.createDummyEvent
import dev.drtheo.mes.model.event.createDummyHomework
import dev.drtheo.mes.ui.theme.DnevnikTheme

@Composable
fun BuildScheduleScreen(
    dnevnikViewModel: DnevnikViewModel,
    onClickLesson: (Event) -> Unit,
    contentPadding: PaddingValues
) {
    ScheduleScreen(
        scheduleUiState = dnevnikViewModel.scheduleUiState,
        retryAction = dnevnikViewModel::refreshData,
        onClickLesson = onClickLesson,
        contentPadding = contentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    scheduleUiState: ScheduleUiState,
    retryAction: () -> Unit,
    onClickLesson: (Event) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = retryAction
    ) {
        when (scheduleUiState) {
            is ScheduleUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is ScheduleUiState.Success -> ScheduleList(
                scheduleUiState.events,
                contentPadding = contentPadding,
                modifier = modifier.fillMaxWidth(),
                onClickLesson = onClickLesson
            )
            is ScheduleUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun ScheduleList(
    events: List<Event>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),

    onClickLesson: (Event) -> Unit,
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(events) { item ->
            EventCard(
                item, onClickLesson,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun EventCard(event: Event, onClickLesson: (Event) -> Unit, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .height(100.dp),
        onClick = { onClickLesson(event) }
    ) {
        Text(
            text = event.name(),
            modifier = Modifier
                .padding(start = 8.dp, top = 4.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        val desc: String? = event.homework?.descriptions?.formatHomework()

        if (desc != null) {
            Text(
                text = desc,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp, end = 4.dp),
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleListPreview() {
    DnevnikTheme {
        val mockData = List(1) {
            createDummyEvent(
                it.toLong(),
                finishAt = "2025-02-14",
                source = "whatever",
                startAt = "2025-02-14",
                title = "Subject No.$it",
                homework = createDummyHomework(
                    arrayListOf("Homework 1", "Homework 2")
                )
            )
        }

        ScheduleList(mockData, onClickLesson = { println("Clicked lesson: $it") })
    }
}