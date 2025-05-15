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

@Composable
fun ExamCard(
    question: Questions,
    questionNumber: Int,
    onAnswerSelected: (Int) -> Unit,
    isSubmitted: Boolean
) {
    val selectedAnswerId = remember { mutableStateOf<Int?>(null) }
    val correctAnswer = remember { mutableStateOf<String?>(null) }
    val answerLabels = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Câu $questionNumber: ${question.question_text}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        question.answers.forEachIndexed { index, answer ->
            val isSelected = selectedAnswerId.value == answer.id
            val backgroundColor = if (isSubmitted && answer.is_correct) {
                Color(0xFFDFF0D8) // Màu xanh lá cho câu trả lời đúng sau khi nộp bài
            } else if (isSubmitted && isSelected && !answer.is_correct) {
                Color(0xFFF2DEDE) // Màu đỏ cho câu trả lời sai sau khi nộp bài
            } else {
                MaterialTheme.colorScheme.surface // Màu nền mặc định
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = !isSubmitted) { // Người dùng có thể thay đổi lựa chọn nếu chưa nộp bài
                        selectedAnswerId.value = answer.id
                        onAnswerSelected(answer.id)
                    },
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                border = BorderStroke(1.dp, if (isSelected) Color.Black else Color.Transparent)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${answerLabels.getOrNull(index) ?: ""}.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = answer.answer_text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )

                    // Chỉ hiển thị icon Đúng/Sai khi đã nộp bài
                    if (isSubmitted && isSelected) {
                        Icon(
                            imageVector = if (answer.is_correct) Icons.Default.Check else Icons.Default.Clear,
                            contentDescription = null,
                            tint = if (answer.is_correct) Color(0xFF4CAF50) else Color.Red
                        )
                    }
                }
            }
        }

        // Hiển thị câu trả lời đúng sau khi nộp bài
        if (isSubmitted) {
            val correct = question.answers.find { it.is_correct }
            correctAnswer.value = if (correct != null) {
                val correctIndex = question.answers.indexOf(correct)
                "${answerLabels[correctIndex]}. ${correct.answer_text}"
            } else {
                "Không có câu trả lời đúng"
            }

            Text(
                text = "Câu trả lời đúng là: ${correctAnswer.value}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}