package com.example.app_tblxa1.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_tblxa1.ui.theme.components.ExamCard
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_tblxa1.viewmodel.ExamViewModel
import com.example.app_tblxa1.viewmodel.QuestionViewModel

@Composable
fun ExamScreen(
    navController: NavHostController,
    questionViewModel: QuestionViewModel = viewModel(),
    examViewModel: ExamViewModel = viewModel(),
    typeTest: String,
    onSubmitExam: (Int, Boolean) -> Unit
) {
    val questions by questionViewModel.questions.collectAsState()
    val errorMessage by questionViewModel.errorMessage.collectAsState()
    val answerResults by examViewModel.answerResults.collectAsState()

    var selectedAnswers by remember { mutableStateOf(mutableMapOf<Int, Int>()) }
    var timeLeft by remember { mutableStateOf(19 * 60) }
    var isSubmitted by remember { mutableStateOf(false) }

    LaunchedEffect(typeTest) {
        questionViewModel.fetchQuestionsByTypeTest(typeTest)
    }

    if (questions.isEmpty() && errorMessage == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (errorMessage != null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = errorMessage ?: "Error", color = MaterialTheme.colorScheme.error)
        }
    } else {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = "Thời gian còn lại: ${timeLeft / 60}:${String.format("%02d", timeLeft % 60)}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(questions.size) { index ->
                    ExamCard(
                        question = questions[index],
                        questionNumber = index + 1,
                        onAnswerSelected = { answerId ->
                            selectedAnswers[questions[index].id] = answerId
                            examViewModel.updateAnswerResult(
                                questions[index].id,
                                isCorrect = questions[index].answers.find { it.id == answerId }?.is_correct == true
                            )
                        },
                        isSubmitted = isSubmitted
                    )
                }
            }

            Button(
                onClick = {
                    if (!isSubmitted) {
                        isSubmitted = true
                        examViewModel.calculateScore(questions, selectedAnswers) { score, hasPassed ->
                            onSubmitExam(score, hasPassed)
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            ) {
                Text(text = "Nộp bài")
            }
        }
    }
}