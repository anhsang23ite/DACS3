package com.example.app_tblxa1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_tblxa1.screens.MainScreen
import com.example.app_tblxa1.ui.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("theory") { TheoryScreen(navController) }
        composable("exam") { ExamScreen(navController) }
        composable("traffic_screen") { TrafficScreen(navController) }
        composable("tips_screen") { TipsScreen(navController) }
        composable(
            "questions_screen/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            QuestionsScreen(navController, category)
        }
    }
}