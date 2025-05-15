package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_tblxa1.viewmodel.ExamViewModel
import com.example.app_tblxa1.ui.theme.components.ExamCard
import com.example.app_tblxa1.viewmodel.QuestionViewModel

@Composable
fun ExamScreen(
    onExamFinished: (Int, Boolean) -> Unit,
    navController: NavHostController,
    questionViewModel: QuestionViewModel,
    examViewModel: ExamViewModel,
    typeTest: String
) {
    val viewModel: ExamViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchExamQuestions()
        viewModel.startTimer {
            viewModel.submitExam(onExamFinished)
        }
    }

    val questions = viewModel.examQuestions.collectAsState()
    val selectedAnswers = viewModel.selectedAnswers.collectAsState()
    val timeRemaining = viewModel.timeRemaining.collectAsState()
    val isExamSubmitted = viewModel.isExamSubmitted.collectAsState()

    if (isExamSubmitted.value) {
        Text(
            text = "Bài thi đã được nộp!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Thời gian còn lại: ${timeRemaining.value / 60}:${timeRemaining.value % 60}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(questions.value) { index, question ->
                ExamCard(
                    question = question,
                    questionNumber = index + 1,
                    onAnswerSelected = { questionId, answerId ->
                        viewModel.selectAnswer(questionId, answerId)
                    },
                    selectedAnswerId = selectedAnswers.value[question.id]
                )
            }
        }

        Button(
            onClick = { viewModel.submitExam(onExamFinished) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Nộp bài")
        }
    }
}