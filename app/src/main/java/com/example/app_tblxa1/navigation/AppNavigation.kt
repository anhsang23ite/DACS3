package com.example.app_tblxa1.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_tblxa1.screens.MainScreen
import com.example.app_tblxa1.ui.screens.ExamScreen
import com.example.app_tblxa1.ui.screens.QuestionsScreen
import com.example.app_tblxa1.ui.screens.TheoryScreen
import com.example.app_tblxa1.ui.screens.TrafficScreen
import com.example.app_tblxa1.ui.screens.TipsScreen
import com.example.app_tblxa1.ui.theme.screens.ExamResultScreen
import com.example.app_tblxa1.viewmodel.ExamViewModel
import com.example.app_tblxa1.viewmodel.QuestionViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("theory") { TheoryScreen(navController) }

        composable(
            "exam/{typeTest}",
            arguments = listOf(navArgument("typeTest") { type = NavType.StringType })
        ) { backStackEntry ->
            val typeTest = backStackEntry.arguments?.getString("typeTest") ?: ""

            if (!QuestionViewModel().isTypeTestValid(typeTest)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Loại bài thi không hợp lệ!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                val questionViewModel: QuestionViewModel = viewModel()
                val examViewModel: ExamViewModel = viewModel()

                ExamScreen(
                    onExamFinished = { score, hasPassed ->
                        navController.navigate("exam_result/$score/$hasPassed")
                    },
                    navController = navController,
                    questionViewModel = questionViewModel,
                    examViewModel = examViewModel,
                    typeTest = typeTest
                )
            }
        }

        composable("exam_result/{score}/{hasPassed}",
            arguments = listOf(
                navArgument("score") { type = NavType.IntType },
                navArgument("hasPassed") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val hasPassed = backStackEntry.arguments?.getBoolean("hasPassed") ?: false
            ExamResultScreen(score, hasPassed, navController)
        }

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

