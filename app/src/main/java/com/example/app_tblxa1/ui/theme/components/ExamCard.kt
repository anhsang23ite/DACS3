package com.example.app_tblxa1.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_tblxa1.model.Questions

@Composable
fun ExamCard(
    question: Questions,
    questionNumber: Int,
    onAnswerSelected: (Int, Int) -> Unit,
    selectedAnswerId: Int?,
    isCorrect: Boolean?,
    isAnswerSelected: Boolean,
    isExamSubmitted: Boolean,
    correctAnswerText: String?
) {
    val answerLabels = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Câu $questionNumber: ${question.question_text}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        question.answers.forEachIndexed { index, answer ->
            val isSelected = answer.id == selectedAnswerId
            val backgroundColor = when {
                isSelected && isCorrect == true -> MaterialTheme.colorScheme.primaryContainer
                isSelected && isCorrect == false -> MaterialTheme.colorScheme.errorContainer
                isSelected -> MaterialTheme.colorScheme.surfaceVariant
                else -> MaterialTheme.colorScheme.surface
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onAnswerSelected(question.id, answer.id) },
                colors = CardDefaults.cardColors(containerColor = backgroundColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = "${answerLabels.getOrNull(index) ?: ""}.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    Text(
                        text = answer.answer_text,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isExamSubmitted && isCorrect != null) {
            Text(
                text = if (isCorrect) {
                    "Câu trả lời của bạn: Đúng"
                } else {
                    "Câu trả lời của bạn: Sai"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = if (isCorrect) Color(0xFF4CAF50) else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )

            if (correctAnswerText != null) {
                Text(
                    text = "Câu trả lời đúng là: $correctAnswerText",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}