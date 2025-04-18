package dev.drtheo.mes.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.drtheo.mes.ErrorScreen
import dev.drtheo.mes.LoadingScreen
import dev.drtheo.mes.R
import dev.drtheo.mes.model.Mark
import dev.drtheo.mes.model.createDummyMark
import dev.drtheo.mes.model.wrapped.MarkData
import dev.drtheo.mes.ui.DnevnikViewModel
import dev.drtheo.mes.ui.MarksUiState

@Composable
fun BuildMarksScreen(
    dnevnikViewModel: DnevnikViewModel,
) {
    MarksScreen(
        marksUiState = dnevnikViewModel.marksUiState,
        retryAction = dnevnikViewModel::refreshData,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarksScreen(
    marksUiState: MarksUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = retryAction
    ) {
        when (marksUiState) {
            is MarksUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is MarksUiState.Success -> MarkList(
                marksUiState.homework,
                modifier = modifier.fillMaxWidth()
            )
            is MarksUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun MarkList(
    events: List<MarkData>,
    modifier: Modifier = Modifier,
) {
    if (events.isEmpty()) {
        Column (
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.size(64.dp),
                painter = painterResource(R.drawable.ic_marks_outline),
                contentDescription = stringResource(R.string.no_marks)
            )

            Text(text = stringResource(R.string.no_marks), modifier = Modifier.padding(16.dp))
        }
    } else {
        LazyColumn(
            modifier = modifier.padding(horizontal = 4.dp),
        ) {
            items(events) { item ->
                item.marks.forEach {
                    Mark(item.subjectName, it)
                }
            }
        }
    }
}

@Composable
fun Mark(title: String, mark: Mark, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .size(48.dp) // Set the size of the box
                .padding(4.dp), // Add padding around the box
            contentAlignment = Alignment.Center // Center the content inside the box
        ) {
            // Draw the rounded rectangle
            Surface(
                modifier = modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(5.dp), // Set the corner radius
                color = MaterialTheme.colorScheme.primaryContainer // Set the surface color
            ) { }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Add the text on top of the rounded rectangle
                Text(
                    text = mark.value,
                    style = TextStyle(
                        fontSize = 20.sp, // Set the text size
                        fontWeight = FontWeight.Bold // Set the text weight
                    )
                )

                if (mark.weight > 1) {
                    Text(
                        text = mark.weight.toString(),
                        style = TextStyle(
                            fontSize = 16.sp, // Smaller font size for the exponent
                            fontWeight = FontWeight.Bold // Set the text weight
                        ),
                        modifier = Modifier
                            .offset(y = (-8).dp) // Move the exponent slightly upward
                    )
                }
            }
        }

        Column {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            val hasComment = mark.comment != null
            val comment = if (hasComment) mark.comment!! else stringResource(R.string.no_comment)

            Text(
                text = comment,
                fontStyle = if (hasComment) FontStyle.Normal else FontStyle.Italic
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMarks() {
    Mark("Алгебра", createDummyMark("Комментарий", "5", 2))
}