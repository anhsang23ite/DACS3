package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.model.Questions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.google.firebase.database.GenericTypeIndicator
import com.example.app_tblxa1.database.FirebaseClient

class ExamViewModel : ViewModel() {
    private val _answerResults = MutableStateFlow<Map<Int, Boolean?>>(emptyMap())
    val answerResults: StateFlow<Map<Int, Boolean?>> = _answerResults

    suspend fun checkAnswer(questionId: Int, answerId: Int): Boolean {
        return try {
            val questionRef = FirebaseClient.database
                .getReference("questions/$questionId/answers")

            // Lấy danh sách câu trả lời từ Firebase
            val snapshot = withContext(Dispatchers.IO) { questionRef.get().await() }
            val answers = snapshot.getValue(object : GenericTypeIndicator<List<Map<String, Any>>>() {})

            // Tìm câu trả lời có `id` khớp với `answerId` và lấy giá trị `is_correct`
            answers?.firstOrNull { it["id"] == answerId }?.get("is_correct") as? Boolean ?: false
        } catch (e: Exception) {
            println("Error checking answer: ${e.message}")
            false
        }
    }

    fun updateAnswerResult(questionId: Int, isCorrect: Boolean) {
        _answerResults.value = _answerResults.value.toMutableMap().apply {
            this[questionId] = isCorrect // Cập nhật kết quả đúng/sai cho câu hỏi tương ứng
        }
    }

    fun calculateScore(
        questionList: List<Questions>,
        selectedAnswers: Map<Int, Int>,
        onResult: (score: Int, hasPassed: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            var score = 0
            var hasFailedLiet = false

            questionList.forEach { question ->
                val correctAnswer = question.answers.find { it.is_correct }?.id
                val userAnswer = selectedAnswers[question.id]

                if (correctAnswer == userAnswer) {
                    score++
                } else if (question.type_test == "liet") {
                    hasFailedLiet = true
                }
            }

            val hasPassed = !hasFailedLiet && score >= 23
            onResult(score, hasPassed)
        }
    }
}