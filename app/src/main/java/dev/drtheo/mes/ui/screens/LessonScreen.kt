package dev.drtheo.mes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.drtheo.mes.ErrorScreen
import dev.drtheo.mes.LoadingScreen
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.model.event.createDummyEvent
import dev.drtheo.mes.model.event.createDummyHomework

@Composable
fun BuildLessonScreen(
    event: Event,
    contentPadding: PaddingValues
) {
    LessonScreen(
        event = event,
        contentPadding = contentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    event: Event,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    println(event)
    Column(
        modifier = modifier.padding(contentPadding),

    ) {
        Text(event.name(), fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Text(event.name(), fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Text(event.name(), fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Text(event.name(), fontWeight = FontWeight.Bold,
            fontSize = 20.sp)

        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) { }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewLessonScreen() {
    LessonScreen(createDummyEvent(
        id = 0L, finishAt = "dummy",
        source = "dummy", startAt = "dummy",
        title = "Lesson", homework =
            createDummyHomework(listOf())
    ))
}