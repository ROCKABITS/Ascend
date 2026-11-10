package com.rockabits.ascend.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rockabits.ascend.ui.components.AscendBottomBar
import com.rockabits.ascend.ui.components.MainTab
import com.rockabits.ascend.ui.screens.HomeScreen
import com.rockabits.ascend.ui.screens.ProfileScreen
import com.rockabits.ascend.ui.screens.QuestDetailScreen
import com.rockabits.ascend.ui.screens.QuestsScreen
import com.rockabits.ascend.ui.screens.SettingsScreen

private const val QUEST_ROUTE = "quest/{id}"

@Composable
fun AscendApp(state: UiState, viewModel: AscendViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val currentTab = MainTab.entries.firstOrNull { it.route == currentRoute }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (currentTab != null) {
                AscendBottomBar(selected = currentTab, onSelect = { navController.navigateToTab(it) })
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainTab.HOME.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(MainTab.HOME.route) {
                HomeScreen(
                    state = state,
                    onOpenQuest = { navController.navigate("quest/$it") },
                    onOpenProfile = { navController.navigateToTab(MainTab.PROFILE) },
                )
            }
            composable(MainTab.QUESTS.route) {
                QuestsScreen(state = state, onOpenQuest = { navController.navigate("quest/$it") })
            }
            composable(MainTab.PROFILE.route) { ProfileScreen(state = state) }
            composable(MainTab.SETTINGS.route) {
                SettingsScreen(themeMode = state.themeMode, onThemeChange = viewModel::setThemeMode)
            }
            composable(QUEST_ROUTE) { entry ->
                val id = entry.arguments?.getString("id").orEmpty()
                QuestDetailScreen(
                    quest = state.questById(id),
                    completed = state.isCompleted(id),
                    onBack = { navController.popBackStack() },
                    onRedeem = { viewModel.completeQuest(id) },
                )
            }
        }
    }
}

private fun NavHostController.navigateToTab(tab: MainTab) {
    navigate(tab.route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
