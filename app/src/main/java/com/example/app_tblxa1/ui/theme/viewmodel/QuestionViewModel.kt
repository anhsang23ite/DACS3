package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.database.FirebaseClient
import com.example.app_tblxa1.model.AnswerResult
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
                        println("Raw data for question: ${child.value}") // Log dữ liệu thô
                        val question = child.getValue(Questions::class.java)
                        println("Parsed question: $question") // Log sau khi ánh xạ
                        println("Parsed answers: ${question?.answers?.map { "${it.id}: ${it.answer_text}, is_correct: ${it.is_correct}" }}") // Log chi tiết answers
                        question?.let { questionList.add(it) }
                    }
                    _questions.value = questionList
                } catch (e: Exception) {
                    println("Error parsing data: ${e.message}")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.value = "Firebase error: ${error.message}"
            }
        })
    }

    suspend fun checkAnswer(questionId: Int, selectedAnswerId: Int): AnswerResult {
        return try {
            val questionRef = FirebaseClient.database.getReference("questions/$questionId")
            val snapshot = withContext(Dispatchers.IO) { questionRef.get().await() }

            var isCorrect = false
            var correctAnswerText: String? = null

            val answers = snapshot.child("answers").children
            for (answer in answers) {
                val answerIdFromFirebase = answer.child("id").getValue(Int::class.java)
                val isCorrectFromFirebase = answer.child("is_correct").getValue(Boolean::class.java) ?: false
                val answerText = answer.child("answer_text").getValue(String::class.java)

                if (isCorrectFromFirebase) {
                    correctAnswerText = answerText
                }

                if (answerIdFromFirebase == selectedAnswerId) {
                    isCorrect = isCorrectFromFirebase
                }
            }

            AnswerResult(isCorrect, correctAnswerText)
        } catch (e: Exception) {
            println("Error checking answer: ${e.message}")
            AnswerResult(false, null)
        }
    }


    fun selectAnswer(questionId: Int, answerId: Int) {
        val question = _questions.value.find { it.id == questionId }
        val isCorrect = question?.answers?.find { it.id == answerId }?.is_correct ?: false

        val updatedResults = _answerResults.value.toMutableMap()
        updatedResults[questionId] = isCorrect
        _answerResults.value = updatedResults
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


    fun fetchQuestionsByTypeTest(typeTest: String) {
        if (!isTypeTestValid(typeTest)) {
            _errorMessage.value = "Loại bài thi không hợp lệ: $typeTest"
            println("Invalid typeTest: $typeTest")
            return
        }

        println("Fetching questions for typeTest: $typeTest")
        val query = FirebaseClient.database
            .getReference("questions")
            .orderByChild("type_test")
            .equalTo(typeTest)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                println("Firebase snapshot received: ${snapshot.value}")
                println("Number of questions found: ${snapshot.childrenCount}")
                try {
                    val questionList = mutableListOf<Questions>()
                    for (child in snapshot.children) {
                        println("Raw data for question: ${child.value}")
                        val question = child.getValue(Questions::class.java)
                        println("Parsed question: $question")
                        println("Parsed answers: ${question?.answers?.map { "${it.id}: ${it.answer_text}, is_correct: ${it.is_correct}" }}")
                        question?.let { questionList.add(it) }
                    }

                    if (questionList.isEmpty()) {
                        println("No questions found for type_test: $typeTest")
                        _errorMessage.value = "Không tìm thấy câu hỏi cho loại bài thi này!"
                    } else {
                        _questions.value = questionList
                        println("Questions loaded successfully: ${questionList.size} questions")
                    }
                } catch (e: Exception) {
                    println("Error parsing data: ${e.message}")
                    _errorMessage.value = "Lỗi khi tải câu hỏi: ${e.message}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase error: ${error.message}")
                _errorMessage.value = "Lỗi Firebase: ${error.message}"
            }
        })
    }

    fun isTypeTestValid(typeTest: String): Boolean {
        return typeTest == "liet" || typeTest == "thuong"
    }
}