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
import com.example.app_tblxa1.ui.screens.*
import com.example.app_tblxa1.ui.theme.screens.ExamScreen
import com.example.app_tblxa1.ui.theme.screens.ReviewScreen
import com.example.app_tblxa1.viewmodel.ExamViewModel
import com.example.app_tblxa1.viewmodel.QuestionViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val questionViewModel: QuestionViewModel = viewModel()
    val examViewModel: ExamViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {
        composable("sign_in") { SignInScreen(navController, viewModel()) }
        composable("sign_up") { SignUpScreen(navController, viewModel()) }
        composable("main") { MainScreen(navController) }
        composable("theory") { TheoryScreen(navController) }
        composable("traffic_screen") { TrafficScreen(navController) }
        composable("tips_screen") { TipsScreen(navController) }
        composable("wrong_questions") { WrongQuestionsScreen(navController) }
        composable(
            "exam/{typeTest}",
            arguments = listOf(navArgument("typeTest") { type = NavType.StringType })
        ) { backStackEntry ->
            val typeTest = backStackEntry.arguments?.getString("typeTest") ?: ""

            if (!questionViewModel.isTypeTestValid(typeTest)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Loại bài thi không hợp lệ!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                ExamScreen(
                    navController = navController,
                    questionViewModel = questionViewModel,
                    examViewModel = examViewModel,
                    typeTest = typeTest,
                    onExamFinished = {
                        navController.navigate("exam_result")
                    }

                )
            }
        }



        composable("exam_result") {
            ResultScreen(
                navController = navController,
                examViewModel = examViewModel
            )
        }

        composable("review") {
            ReviewScreen(
                navController = navController,
                examViewModel = examViewModel
            )
        }

        composable(
            "questions_screen/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            QuestionsScreen(navController, category)
        }
    }
}
