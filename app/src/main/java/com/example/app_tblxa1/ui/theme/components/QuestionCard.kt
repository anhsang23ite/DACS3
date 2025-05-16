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
    onAnswerSelected: suspend (Int, Int) -> Boolean
) {
    val selectedAnswerId = remember { mutableStateOf<Int?>(null) }
    val isAnswerCorrect = remember { mutableStateOf<Boolean?>(null) }
    val correctAnswer = remember { mutableStateOf<String?>(null) }
    val answerLabels = listOf("A", "B", "C", "D", "E")
    val coroutineScope = rememberCoroutineScope()

    println("Question answers in QuestionCard: ${question.answers.map { "${it.id}: ${it.answer_text}, is_correct: ${it.is_correct}" }}")
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

        question.answers.forEachIndexed { index, answers ->
            val isSelected = selectedAnswerId.value == answers.id
            val backgroundColor = when {
                !isSelected -> MaterialTheme.colorScheme.surface
                isAnswerCorrect.value == true -> Color(0xFFDFF0D8)
                isAnswerCorrect.value == false -> Color(0xFFF2DEDE)
                else -> MaterialTheme.colorScheme.surface
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = isAnswerCorrect.value == null) {
                        selectedAnswerId.value = answers.id
                        coroutineScope.launch {
                            val result = withContext(Dispatchers.IO) {
                                onAnswerSelected(question.id, answers.id)
                            }
                            isAnswerCorrect.value = result
                            println("Check result for answer ${answers.id}: $result, Answers state: ${question.answers.map { "${it.id}: ${it.answer_text}, is_correct: ${it.is_correct}" }}")
                            val correct = question.answers.find { it.is_correct }
                            correctAnswer.value = correct?.let {
                                val correctIndex = question.answers.indexOf(it)
                                "${answerLabels[correctIndex]}. ${it.answer_text}"
                            } ?: "Không có câu trả lời đúng"
                            println("Correct answer set to: ${correctAnswer.value}")
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