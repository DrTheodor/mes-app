package dev.drtheo.mes.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.drtheo.mes.ErrorScreen
import dev.drtheo.mes.LoadingScreen
import dev.drtheo.mes.formatHomework
import dev.drtheo.mes.model.wrapped.Homework
import dev.drtheo.mes.ui.DnevnikViewModel
import dev.drtheo.mes.ui.HomeworkUiState

@Composable
fun BuildHomeworkScreen(
    dnevnikViewModel: DnevnikViewModel,
) {
    HomeworkScreen(
        homeworkUiState = dnevnikViewModel.homeworkUiState,
        retryAction = dnevnikViewModel::refreshData,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkScreen(
    homeworkUiState: HomeworkUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = retryAction,
    ) {
        when (homeworkUiState) {
            is HomeworkUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is HomeworkUiState.Success -> HomeworkList(
                homeworkUiState.homework,
                modifier = modifier.fillMaxWidth()
            )
            is HomeworkUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
        }
    }
}

@Composable
fun HomeworkList(
    homework: List<Homework>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(homework) { item ->
            HomeworkCard(
                item.subjectName, item,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun HomeworkCard(title: String, homework: Homework, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
    ) {
        Text(
            text = homework.descriptions.formatHomework(),
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp, end = 4.dp),
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp
        )
    }
}