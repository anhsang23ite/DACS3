package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_tblxa1.ui.theme.components.WrongQuestionCard
import com.example.app_tblxa1.viewmodel.WrongQuestionViewModel

@Composable
fun WrongQuestionsScreen(navController: NavHostController) {
    val viewModel: WrongQuestionViewModel = viewModel()
    val questions = viewModel.questions
    val errorMessage = viewModel.errorMessage

    Column(modifier = Modifier.fillMaxSize()) {
        errorMessage.value?.let {
            Text(
                text = "Lỗi: $it",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (questions.value.isEmpty()) {
            Text(
                text = "Không có câu hỏi sai để hiển thị.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(questions.value) { index, question ->
                    WrongQuestionCard(
                        question = question,
                        questionNumber = index + 1,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}