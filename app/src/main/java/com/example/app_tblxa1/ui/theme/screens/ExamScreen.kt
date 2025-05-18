package com.example.app_tblxa1.ui.theme.screens

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.ui.theme.components.ExamCard
import com.example.app_tblxa1.viewmodel.ExamViewModel
import com.example.app_tblxa1.viewmodel.QuestionViewModel

@Composable
fun ExamScreen(
    navController: NavHostController,
    questionViewModel: QuestionViewModel,
    examViewModel: ExamViewModel,
    typeTest: String,
    context: Context = LocalContext.current,
    onExamFinished: Function<Unit>
) {
    LaunchedEffect(Unit) {
        examViewModel.resetExam()
        examViewModel.fetchExamQuestions()
        examViewModel.startTimer {
            examViewModel.submitExam { _, _ ->
                navController.navigate("review")
            }
        }
    }

    val questions = examViewModel.examQuestions.collectAsState()
    val selectedAnswers = examViewModel.selectedAnswers.collectAsState()
    val isAnswerSelected = examViewModel.isAnswerSelected.collectAsState()
    val timeRemaining = examViewModel.timeRemaining.collectAsState()
    val isExamSubmitted = examViewModel.isExamSubmitted.collectAsState()
    val errorMessage = examViewModel.errorMessage.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Thời gian còn lại: ${timeRemaining.value / 60}:${String.format("%02d", timeRemaining.value % 60)}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isExamSubmitted.value) {
            Text(
                text = "Đang chuyển sang màn hình xem lại...",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            errorMessage.value?.let { error ->
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = { examViewModel.fetchExamQuestions() },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    Text("Thử lại")
                }
            } ?: if (questions.value.isEmpty()) {
                Text(
                    text = "Đang tải câu hỏi...",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(questions.value) { index, question ->
                        ExamCard(
                            question = question,
                            questionNumber = index + 1,
                            onAnswerSelected = { questionId, answerId ->
                                println("Chọn đáp án trong ExamCard Q$questionId: A$answerId")
                                examViewModel.selectAnswer(questionId, answerId, context)
                            },
                            selectedAnswerId = selectedAnswers.value[question.id],
                            isAnswerSelected = isAnswerSelected.value[question.id] ?: false,
                            isCorrect = null, // Không hiển thị đúng/sai trước nộp
                            isExamSubmitted = isExamSubmitted.value
                        )
                    }
                }
            }

            Button(
                onClick = {
                    println("Trước khi nộp: ${examViewModel.selectedAnswers.value}")
                    examViewModel.submitExam { _, _ ->
                        navController.navigate("review")
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                enabled = questions.value.isNotEmpty()
            ) {
                Text("Nộp bài")
            }
        }
    }
}