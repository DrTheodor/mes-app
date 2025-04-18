package dev.drtheo.mes.ui.compose

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import dev.drtheo.mes.BuiltInDnevnikScreen
import dev.drtheo.mes.DnevnikScreen

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
                label = {
                    Text(
                        title,
                        textAlign = TextAlign.Center,
                    )
                }
            )
        }
    }
}

@Composable
private fun TabBarDirectIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = title
        )
    }
}

@Composable
private fun TabBarPainterIconView(
    isSelected: Boolean,
    selectedPainter: Painter,
    unselectedPainter: Painter,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            painter = if (isSelected) selectedPainter else unselectedPainter,
            contentDescription = title
        )
    }
}

@Composable
private fun TabBarBadgeView(count: Int? = null) {
    if (count == null)
        return

    Badge {
        Text(count.toString())
    }
}