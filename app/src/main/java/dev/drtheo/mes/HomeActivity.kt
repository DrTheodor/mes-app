package dev.drtheo.mes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.drtheo.mes.model.event.Event
import dev.drtheo.mes.ui.screens.BuildHomeworkScreen
import dev.drtheo.mes.ui.screens.BuildLessonScreen
import dev.drtheo.mes.ui.screens.BuildMarksScreen
import dev.drtheo.mes.ui.screens.BuildScheduleScreen
import dev.drtheo.mes.ui.screens.DnevnikViewModel
import dev.drtheo.mes.ui.theme.DnevnikTheme
import kotlinx.serialization.Serializable

@Serializable
sealed class DnevnikScreen {
    abstract val route: String

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
    )
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
            currentScreen = currentScreen,
            scrollBehavior = scrollBehavior,
            canNavigateBack = navController.previousBackStackEntry != null && currentScreen !is BuiltInDnevnikScreen,
            navigateUp = { navController.navigateUp() }
        ) },
        bottomBar = { BottomNavBar(navController) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val padding = it

            NavHost(navController = navController, startDestination = startScreen.route) {
                composable(Screens.Schedule.route) {
                    BuildScheduleScreen(
                        dnevnikViewModel,
                        onClickLesson = {
                            dnevnikViewModel.selectedEvent(it)
                            navController.navigate(Screens.Lesson.route)
                        },
                        padding
                    )
                }

                composable(Screens.Homework.route) {
                    BuildHomeworkScreen(dnevnikViewModel, padding)
                }

                composable(Screens.Marks.route) {
                    BuildMarksScreen(dnevnikViewModel, padding)
                }

                composable(Screens.Lesson.route) {
                    val event: Event = dnevnikViewModel.selectedEvent!!
                    BuildLessonScreen(event, padding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DnevnikTopAppBar(
    currentScreen: DnevnikScreen,
    scrollBehavior: TopAppBarScrollBehavior,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val title = when (currentScreen) {
        is BuiltInDnevnikScreen -> stringResource(currentScreen.titleRes)
        is CustomDnevnikScreen -> currentScreen.title
    }

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
}

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        DnevnikScreen.Entries.forEachIndexed { index, tabBarItem ->
            if (tabBarItem !is BuiltInDnevnikScreen)
                return@forEachIndexed

            val title = stringResource(tabBarItem.titleRes)

            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.route)
                },
                icon = {
                    if (tabBarItem.selectedIcon != null && tabBarItem.unselectedIcon != null) {
                        TabBarDirectIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = title,
                        )
                    } else if (tabBarItem.selectedPainter != null && tabBarItem.unselectedPainter != null) {
                        TabBarPainterIconView(
                            isSelected = selectedTabIndex == index,
                            selectedPainter = painterResource(tabBarItem.selectedPainter),
                            unselectedPainter = painterResource(tabBarItem.unselectedPainter),
                            title = title,
                        )
                    }
                },
                label = { Text(title) })
        }
    }
}

@Composable
fun TabBarDirectIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

@Composable
fun TabBarPainterIconView(
    isSelected: Boolean,
    selectedPainter: Painter,
    unselectedPainter: Painter,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            painter = if (isSelected) {selectedPainter} else {unselectedPainter},
            contentDescription = title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count == null)
        return

    Badge {
        Text(count.toString())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(100.dp),
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