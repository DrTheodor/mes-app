package dev.drtheo.mes

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.ui.DnevnikViewModel
import dev.drtheo.mes.ui.compose.BottomNavBar
import dev.drtheo.mes.ui.compose.DnevnikTopAppBar
import dev.drtheo.mes.ui.screens.BuildHomeworkScreen
import dev.drtheo.mes.ui.screens.BuildLessonScreen
import dev.drtheo.mes.ui.screens.BuildMarksScreen
import dev.drtheo.mes.ui.screens.BuildScheduleScreen
import dev.drtheo.mes.ui.theme.DnevnikTheme
import kotlinx.serialization.Serializable

@Serializable
sealed class DnevnikScreen {
    abstract val route: String
    abstract val hasCalendar: Boolean

    fun init() {
        Lookup[route] = this
        Entries.add(this)
    }

    companion object {
        val Lookup = hashMapOf<String, DnevnikScreen>()
        val Entries = arrayListOf<DnevnikScreen>()
    }
}

abstract class BuiltInDnevnikScreen(
    override val route: String,
    override val hasCalendar: Boolean = true,
    val titleRes: Int,

    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,

    @DrawableRes val selectedPainter: Int? = null,
    @DrawableRes val unselectedPainter: Int? = null,
) : DnevnikScreen() {
    init { init() }
}

@Serializable
abstract class CustomDnevnikScreen(
    override val route: String,
    var title: String
) : DnevnikScreen() {
    init { init() }
}

object Screens {
    object Schedule : BuiltInDnevnikScreen(
        route = "schedule",
        titleRes = R.string.schedule,
        selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.AutoMirrored.Outlined.List
    )

    object Homework : BuiltInDnevnikScreen(
        route = "homework",
        titleRes = R.string.homework,
        selectedPainter = R.drawable.ic_menu_book_filled,
        unselectedPainter = R.drawable.ic_menu_book_outline
    )

    object Marks : BuiltInDnevnikScreen(
        route = "marks",
        titleRes = R.string.marks,
        selectedPainter = R.drawable.ic_marks_filled,
        unselectedPainter = R.drawable.ic_marks_outline
    )

    object Lesson : CustomDnevnikScreen(
        route = "lesson", title = ""
    ) {
        override val hasCalendar: Boolean = false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivity(
    dnevnikViewModel: DnevnikViewModel
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val navController = rememberNavController()
    val startScreen = Screens.Schedule

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = DnevnikScreen.Lookup[backStackEntry?.destination?.route
        ?: startScreen.route]!!

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { DnevnikTopAppBar(
            viewModel = dnevnikViewModel,
            currentScreen = currentScreen,
            scrollBehavior = scrollBehavior,
            canNavigateBack = navController.previousBackStackEntry != null && currentScreen !is BuiltInDnevnikScreen,
            navigateUp = { navController.navigateUp() }
        ) },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
        ) {
            NavHost(
                navController = navController,
                startDestination = startScreen.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(250))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(250))
                },
                popEnterTransition = {
                    fadeIn(animationSpec = tween(250))
                },
                popExitTransition = {
                    fadeOut(animationSpec = tween(250))
                }
            ) {
                composable(Screens.Schedule.route) {
                    BuildScheduleScreen(
                        dnevnikViewModel,
                        onClickLesson = {
                            dnevnikViewModel.selectedEvent(it)
                            navController.navigate(Screens.Lesson.route)
                        }
                    )
                }

                composable(Screens.Homework.route) {
                    BuildHomeworkScreen(dnevnikViewModel)
                }

                composable(Screens.Marks.route) {
                    BuildMarksScreen(dnevnikViewModel)
                }

                composable(Screens.Lesson.route) {
                    val event: Event = dnevnikViewModel.selectedEvent!!
                    BuildLessonScreen(event)
                }
            }
        }
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(64.dp),
        painter = painterResource(R.drawable.ic_loading_outline),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    DnevnikTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    DnevnikTheme {
        ErrorScreen({})
    }
}