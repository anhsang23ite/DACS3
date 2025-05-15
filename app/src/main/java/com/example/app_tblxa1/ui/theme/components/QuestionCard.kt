package com.example.app_tblxa1.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_tblxa1.model.Questions
import com.example.app_tblxa1.viewmodel.QuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun QuestionCard(
    question: Questions, // Đối tượng câu hỏi
    questionNumber: Int, // Số thứ tự câu hỏi
    onAnswerSelected: suspend (Int, Int) -> Boolean // Hàm kiểm tra đáp án
) {
    // Lưu trữ trạng thái ID của câu trả lời được chọn
    val selectedAnswerId = remember { mutableStateOf<Int?>(null) }
    // Lưu trữ trạng thái kết quả đúng/sai
    val isAnswerCorrect = remember { mutableStateOf<Boolean?>(null) }
    // Sử dụng coroutine để thực thi các hàm suspend
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Hiển thị câu hỏi
        Text(
            text = "Câu $questionNumber: ${question.question_text}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hiển thị danh sách các đáp án
        question.answers.forEach { answer ->
            // Xác định đáp án được chọn
            val isSelected = selectedAnswerId.value == answer.id
            // Thay đổi màu nền theo kết quả đúng/sai
            val backgroundColor = when {
                isSelected && isAnswerCorrect.value == true -> Color(0xFFDFF0D8) // Màu xanh lá nếu đúng
                isSelected && isAnswerCorrect.value == false -> Color(0xFFF2DEDE) // Màu đỏ nếu sai
                else -> MaterialTheme.colorScheme.surface
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = isAnswerCorrect.value == null) { // Chỉ cho phép chọn nếu chưa có đáp án
                        selectedAnswerId.value = answer.id // Lưu ID câu trả lời đã chọn
                        coroutineScope.launch {
                            // Gọi hàm kiểm tra đúng/sai
                            val result = onAnswerSelected(question.id, answer.id)
                            isAnswerCorrect.value = result // Lưu kết quả đúng/sai
                        }
                    },
                colors = CardDefaults.cardColors(containerColor = backgroundColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hiển thị nội dung câu trả lời
                    Text(
                        text = answer.answer_text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )

                    // Hiển thị icon đánh dấu đúng/sai nếu đã chọn
                    if (isSelected && isAnswerCorrect.value != null) {
                        Icon(
                            imageVector = if (isAnswerCorrect.value == true) Icons.Default.Check else Icons.Default.Clear,
                            contentDescription = null,
                            tint = if (isAnswerCorrect.value == true) Color.Green else Color.Red
                        )
                    }
                }
            }
        }
    }
}