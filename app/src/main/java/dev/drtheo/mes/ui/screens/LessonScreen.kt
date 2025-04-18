package dev.drtheo.mes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.model.event.createDummyEvent
import dev.drtheo.mes.model.event.createDummyHomework

@Composable
fun BuildLessonScreen(
    event: Event,
) {
    LessonScreen(
        event = event,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    event: Event,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//            modifier = modifier
//                .offset(y = (-8).dp)
        ) {
            Text(event.subjectName, fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLessonScreen() {
    LessonScreen(createDummyEvent(
        id = 0L, finishAt = "dummy",
        source = "dummy", startAt = "dummy",
        homework =
            createDummyHomework(listOf())
    ))
}