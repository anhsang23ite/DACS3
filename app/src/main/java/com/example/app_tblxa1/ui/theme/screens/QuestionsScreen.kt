package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_tblxa1.ui.theme.components.QuestionCard
import com.example.app_tblxa1.viewmodel.QuestionViewModel
import androidx.navigation.NavHostController

@Composable
fun QuestionsScreen(navController: NavHostController, category: String) {
    val viewModel: QuestionViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchQuestions(category)
    }

    val questions = viewModel.questions.collectAsState()
    val answerResults = viewModel.answerResults.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

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
            text = "Không có câu hỏi nào để hiển thị.",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(questions.value) { index, question ->
                QuestionCard(
                    question = question,
                    questionNumber = index + 1,
                    onAnswerSelected = { questionId, answerId ->
                        viewModel.checkAnswer(questionId, answerId) // Hàm này phải trả về Boolean
                    }
                )

                answerResults.value[question.id]?.let { isCorrect ->
                    Text(
                        text = if (isCorrect) "Đáp án đúng!" else "Đáp án sai!",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    )
                }
            }
        }
    }
}