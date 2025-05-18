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
import com.example.app_tblxa1.viewmodel.WrongQuestionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun WrongQuestionCard(
    question: Questions,
    questionNumber: Int,
    viewModel: WrongQuestionViewModel,
    onAnswerSelected: (Int, Int) -> Unit = { _, _ -> }
) {
    var selectedAnswerId by remember { mutableStateOf<Int?>(null) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var correctIonic by remember { mutableStateOf<String?>(null) }
    val answerLabels = listOf("A", "B", "C", "D", "E")
    val coroutineScope = rememberCoroutineScope()

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
            val isSelected = selectedAnswerId == answer.id
            val backgroundColor = when {
                !isSelected -> MaterialTheme.colorScheme.surface
                isAnswerCorrect == true -> Color(0xFFDFF0D8)
                isAnswerCorrect == false -> Color(0xFFF2DEDE)
                else -> MaterialTheme.colorScheme.surface
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable(enabled = isAnswerCorrect == null) {
                        selectedAnswerId = answer.id
                        coroutineScope.launch {
                            val result = withContext(Dispatchers.IO) {
                                viewModel.checkAnswer(question.id, answer.id)
                            }
                            isAnswerCorrect = result.isCorrect
                            correctIonic = result.correctAnswerText?.let {
                                val correctIndex = question.answers.indexOfFirst { ans -> ans.answer_text == it }
                                val label = answerLabels.getOrNull(correctIndex) ?: "?"
                                "$label. $it"
                            }
                            onAnswerSelected(question.id, answer.id)
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
                        text = answer.answer_text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    if (isSelected && isAnswerCorrect != null) {
                        Icon(
                            imageVector = if (isAnswerCorrect == true) Icons.Default.Check else Icons.Default.Clear,
                            contentDescription = null,
                            tint = if (isAnswerCorrect == true) Color(0xFF4CAF50) else Color.Red
                        )
                    }
                }
            }
        }

        if (isAnswerCorrect != null) {
            Text(
                text = if (isAnswerCorrect == true) "Đáp án đúng!" else "Đáp án sai!",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isAnswerCorrect == true) Color(0xFF4CAF50) else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Câu trả lời đúng là: ${correctIonic}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Blue,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}