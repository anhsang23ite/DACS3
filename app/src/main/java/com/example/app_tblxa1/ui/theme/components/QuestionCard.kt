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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun QuestionCard(
    question: Questions,
    questionNumber: Int,
    onAnswerSelected: suspend (Int, Int) -> Boolean // Firebase kiểm tra đáp án
) {
    val selectedAnswerId = remember { mutableStateOf<Int?>(null) }
    val isAnswerCorrect = remember { mutableStateOf<Boolean?>(null) }
    val correctAnswer = remember { mutableStateOf<String?>(null) }
    val answerLabels = listOf("A", "B", "C", "D", "E")
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

        // Danh sách đáp án
        question.answers.forEachIndexed { index, answers ->
            val isSelected = selectedAnswerId.value == answers.id
            val backgroundColor = when {
                !isSelected -> MaterialTheme.colorScheme.surface
                isAnswerCorrect.value == true -> Color(0xFFDFF0D8) // Màu xanh nếu đúng
                isAnswerCorrect.value == false -> Color(0xFFF2DEDE) // Màu đỏ nếu sai
                else -> MaterialTheme.colorScheme.surface
            }
            val correct = question.answers.find { it.is_correct } // Tìm câu trả lời đúng
            if (correct == null) {
                correctAnswer.value = "Không có câu trả lời đúng"
            } else {
                val correctIndex = question.answers.indexOf(correct)
                correctAnswer.value = "${answerLabels[correctIndex]}. ${correct.answer_text}"
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = isAnswerCorrect.value == null) {
                        selectedAnswerId.value = answers.id
                        coroutineScope.launch {
                            // Kiểm tra đáp án từ Firebase
                            val result = withContext(Dispatchers.IO) {
                                onAnswerSelected(question.id, answers.id)
                            }
                            isAnswerCorrect.value = result

                            // Xác định đáp án đúng
                            val correct = question.answers.find { it.is_correct == true }
                            correctAnswer.value = if (correct != null) {
                                val correctIndex = question.answers.indexOf(correct)
                                "${answerLabels[correctIndex]}. ${correct.answer_text}"
                            } else {
                                "Không có câu trả lời đúng"
                            }
                        }
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
                        text = answers.answer_text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )

                    if (isSelected && isAnswerCorrect.value != null) {
                        Icon(
                            imageVector = if (isAnswerCorrect.value == true) Icons.Default.Check else Icons.Default.Clear,
                            contentDescription = null,
                            tint = if (isAnswerCorrect.value == true) Color(0xFF4CAF50) else Color.Red
                        )
                    }
                }
            }
        }

        // Hiển thị kết quả
        if (isAnswerCorrect.value != null) {
            Text(
                text = if (isAnswerCorrect.value == true) {
                    "Câu trả lời của bạn: Đúng"
                } else {
                    "Câu trả lời của bạn: Sai"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (isAnswerCorrect.value == true) Color(0xFF4CAF50) else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Câu trả lời đúng là: ${correctAnswer.value}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}