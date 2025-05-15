package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.database.FirebaseClient
import com.example.app_tblxa1.model.Questions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class QuestionViewModel : ViewModel() {
    private val _questions = MutableStateFlow<List<Questions>>(emptyList())
    val questions: StateFlow<List<Questions>> = _questions

    private val _answerResults = MutableStateFlow<Map<Int, Boolean?>>(emptyMap())
    val answerResults: StateFlow<Map<Int, Boolean?>> = _answerResults

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchQuestions(category: String) {
        val query = FirebaseClient.database
            .getReference("questions")
            .orderByChild("type_learn")
            .equalTo(category)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val questionList = mutableListOf<Questions>()
                    for (child in snapshot.children) {
                        val question = child.getValue(Questions::class.java)
                        if (question != null) {
                            questionList.add(question)
                        } else {
                            println("Warning: Skipping null question")
                        }
                    }
                    _questions.value = questionList
                } catch (e: Exception) {
                    println("Error parsing data: ${e.message}")
                    _errorMessage.value = "Error fetching questions"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase error: ${error.message}")
                _errorMessage.value = "Firebase error: ${error.message}"
            }
        })
    }

    // Thêm hàm fetch dựa theo type_test (typeTest)
    fun fetchQuestionsByTypeTest(typeTest: String) {
        if (!isTypeTestValid(typeTest)) {
            _errorMessage.value = "Loại bài thi không hợp lệ!"
            return
        }

        val query = FirebaseClient.database
            .getReference("questions")
            .orderByChild("type_test")
            .equalTo(typeTest)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val questionList = mutableListOf<Questions>()
                    for (child in snapshot.children) {
                        val question = child.getValue(Questions::class.java)
                        if (question != null) {
                            questionList.add(question)
                        } else {
                            println("Warning: Skipping null question")
                        }
                    }

                    if (questionList.isEmpty()) {
                        println("No questions found for type_test: $typeTest")
                        _errorMessage.value = "Không tìm thấy câu hỏi cho loại bài thi này!"
                    } else {
                        _questions.value = questionList
                        println("Questions loaded successfully: $questionList")
                    }
                } catch (e: Exception) {
                    println("Error parsing data: ${e.message}")
                    _errorMessage.value = "Lỗi khi tải câu hỏi từ Firebase."
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase error by typeTest: ${error.message}")
                _errorMessage.value = "Lỗi Firebase: ${error.message}"
            }
        })
    }

    fun isTypeTestValid(typeTest: String): Boolean {
        return typeTest == "liet" || typeTest == "thuong"
    }

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

    suspend fun onAnswerSelected(questionId: Int, answerId: Int): Boolean {
        return try {
            val questionRef = FirebaseClient.database
                .getReference("questions/$questionId/answers")

            // Lấy danh sách câu trả lời từ Firebase
            val snapshot = withContext(Dispatchers.IO) { questionRef.get().await() }
            val answers = snapshot.getValue(object : GenericTypeIndicator<List<Map<String, Any>>>() {})

            // Tìm câu trả lời có `id` khớp với `answerId` và lấy giá trị `is_correct`
            answers?.firstOrNull { it["id"] == answerId }?.get("is_correct") as? Boolean ?: false
        } catch (e: Exception) {
            println("Error fetching answer correctness: ${e.message}")
            false
        }
    }



    fun updateAnswerResult(questionId: Int, isCorrect: Boolean) {
        _answerResults.value = _answerResults.value.toMutableMap().apply {
            this[questionId] = isCorrect // Cập nhật kết quả đúng/sai cho câu hỏi tương ứng
        }
    }

    fun calculateScore(
        selectedAnswers: Map<Int, Int>,
        onResult: (score: Int, hasPassed: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val questionList = _questions.value ?: emptyList()
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