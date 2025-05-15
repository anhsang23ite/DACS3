package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_tblxa1.ui.theme.components.QuestionCard
import com.example.app_tblxa1.viewmodel.QuestionViewModel
import androidx.navigation.NavHostController

@Composable
fun QuestionsScreen(navController: NavHostController,
                    category: String) {
    val viewModel: QuestionViewModel = viewModel()

    // Chạy khi màn hình được tải, dùng để lấy danh sách câu hỏi
    LaunchedEffect(Unit) {
        viewModel.fetchQuestions(category)
    }

    // Lấy trạng thái dữ liệu từ ViewModel
    val questions = viewModel.questions.collectAsState()
    val answerResults = viewModel.answerResults.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // Hiển thị lỗi nếu có lỗi xảy ra
    errorMessage.value?.let { error ->
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Lỗi: $error", // Thông báo lỗi
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                onClick = { viewModel.fetchQuestions(category) }, // Nút để thử lại
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Thử lại")
            }
        }
        return // Kết thúc nếu có lỗi
    }

    // Hiển thị trạng thái loading nếu chưa có dữ liệu câu hỏi và không có lỗi
    if (questions.value.isEmpty() && errorMessage.value == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // Hiển thị vòng tròn chờ
        }
        return // Kết thúc nếu đang loading
    }

    // Hiển thị danh sách câu hỏi
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(questions.value) { index, question ->
            // Gọi QuestionCard để hiển thị từng câu hỏi
            QuestionCard(
                question = question,
                questionNumber = index + 1,
                onAnswerSelected = { questionId, answerId ->
                    // Gọi ViewModel để kiểm tra đáp án
                    val isCorrect = viewModel.checkAnswer(questionId, answerId)
                    viewModel.updateAnswerResult(questionId, isCorrect) // Cập nhật kết quả vào ViewModel
                    isCorrect // Trả về kết quả đúng/sai
                }
            )

            // Hiển thị kết quả của câu trả lời nếu đã có
            answerResults.value[question.id]?.let { isCorrect ->
                Text(
                    text = if (isCorrect) "Đáp án đúng!" else "Đáp án sai!", // Hiển thị kết quả
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                )
            }
        }
    }
}