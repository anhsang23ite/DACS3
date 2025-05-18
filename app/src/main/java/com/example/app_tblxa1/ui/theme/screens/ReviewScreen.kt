package com.example.app_tblxa1.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.model.AnswerResult
import com.example.app_tblxa1.ui.theme.components.ExamCard
import com.example.app_tblxa1.viewmodel.ExamViewModel

@Composable
fun ReviewScreen(
    navController: NavHostController,
    examViewModel: ExamViewModel
) {
    val questions = examViewModel.examQuestions.collectAsState()
    val selectedAnswers = examViewModel.selectedAnswers.collectAsState()
    val answerResults = examViewModel.answerResults.collectAsState()
    val isAnswerSelected = examViewModel.isAnswerSelected.collectAsState()
    val score = examViewModel.score.collectAsState()
    val hasFailedLiet = examViewModel.hasFailedLiet.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Kết quả bài thi",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Điểm: ${score.value}/25",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        val hasPassed = !hasFailedLiet.value && score.value >= 23
        Text(
            text = if (hasPassed) "Đậu" else "Rớt${if (hasFailedLiet.value) " (Sai câu liệt)" else ""}",
            style = MaterialTheme.typography.bodyLarge,
            color = if (hasPassed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(questions.value) { index, question ->
                ExamCard(
                    question = question,
                    questionNumber = index + 1,
                    onAnswerSelected = { _, _ -> }, // Disable answer selection
                    selectedAnswerId = selectedAnswers.value[question.id],
                    isAnswerSelected = isAnswerSelected.value[question.id] ?: false,
                    isCorrect = answerResults.value[question.id]?.isCorrect,
                    isExamSubmitted = true, // Always show feedback
                    correctAnswerText = answerResults.value[question.id]?.correctAnswerText
                )
            }
        }

        Button(
            onClick = {
                examViewModel.resetExam()
                navController.navigate("main") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Trở về trang chủ")
        }
    }
}