package com.example.app_tblxa1.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app_tblxa1.model.Questions

@Composable
fun ExamCard(
    question: Questions,
    questionNumber: Int,
    onAnswerSelected: (Int, Int) -> Unit,
    selectedAnswerId: Int?,
    isCorrect: Boolean?, // Thêm tham số để hiển thị đúng/sai
    isAnswerSelected: Boolean,
    isExamSubmitted: Boolean
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
    }
}