package com.example.moblilkiprojekt.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moblilkiprojekt.ui.screens.TaskListScreen
import com.example.moblilkiprojekt.ui.screens.AddTaskScreen
import com.example.moblilkiprojekt.ui.screens.TaskDetailScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier
    ) {
        composable("list") {
            TaskListScreen(navController = navController)
        }
        composable("add") {
            AddTaskScreen(navController = navController)
        }
        composable("detail/{taskId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("taskId")?.toLongOrNull() ?: return@composable
            TaskDetailScreen(navController = navController, taskId = id)
        }


    }
}
