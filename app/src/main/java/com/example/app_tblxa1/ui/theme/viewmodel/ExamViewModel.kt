package com.example.app_tblxa1.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.database.FirebaseClient
import com.example.app_tblxa1.model.AnswerResult
import com.example.app_tblxa1.model.Answers
import com.example.app_tblxa1.model.Questions
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ExamViewModel : ViewModel() {
    private val _examQuestions = MutableStateFlow<List<Questions>>(emptyList())
    val examQuestions: StateFlow<List<Questions>> get() = _examQuestions

    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, Int>> get() = _selectedAnswers

    private val _answerResults = MutableStateFlow<Map<Int, AnswerResult>>(emptyMap())
    val answerResults: StateFlow<Map<Int, AnswerResult>> get() = _answerResults

    private val _isAnswerSelected = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val isAnswerSelected: StateFlow<Map<Int, Boolean>> get() = _isAnswerSelected

    private val _timeRemaining = MutableStateFlow(19 * 60)
    val timeRemaining: StateFlow<Int> get() = _timeRemaining

    private val _isExamSubmitted = MutableStateFlow(false)
    val isExamSubmitted: StateFlow<Boolean> get() = _isExamSubmitted

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    private val _hasFailedLiet = MutableStateFlow(false)
    val hasFailedLiet: StateFlow<Boolean> get() = _hasFailedLiet

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    fun fetchExamQuestions() = viewModelScope.launch {
        try {
            val snapshot = FirebaseClient.database.getReference("questions").get().await()
            val allQuestions = snapshot.children.mapNotNull { snapshot ->
                try {
                    val rawData = snapshot.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                    println("Dữ liệu thô Q${snapshot.key}: $rawData")

                    val id = snapshot.key?.toIntOrNull() ?: (rawData?.get("id") as? Number)?.toInt() ?: 0
                    val questionText = rawData?.get("question_text") as? String ?: ""
                    val imageUrl = rawData?.get("image_url") as? String
                    val typeTest = rawData?.get("type_test") as? String ?: ""
                    val typeLearn = rawData?.get("type_learn") as? String ?: ""
                    val rawAnswers = rawData?.get("answers") as? List<Map<String, Any>> ?: emptyList()

                    val answers = rawAnswers.map { answerMap ->
                        val answerId = when (val idValue = answerMap["id"]) {
                            is Long -> idValue.toInt()
                            is Int -> idValue
                            is String -> idValue.toIntOrNull() ?: 0
                            else -> 0
                        }
                        val answerText = answerMap["answer_text"] as? String ?: ""
                        val isCorrect = when (val isCorrectValue = answerMap["is_correct"]) {
                            is Boolean -> isCorrectValue
                            is String -> isCorrectValue.toBoolean()
                            else -> false
                        }
                        Answers(
                            id = answerId,
                            answer_text = answerText,
                            is_correct = isCorrect
                        )
                    }

                    Questions(
                        id = id,
                        question_text = questionText,
                        image_url = imageUrl,
                        type_test = typeTest,
                        type_learn = typeLearn,
                        answers = answers
                    )
                } catch (e: Exception) {
                    println("Lỗi ánh xạ Q${snapshot.key}: ${e.message}")
                    null
                }
            }
            println("Đã tải ${allQuestions.size} câu hỏi từ Firebase (questions)")

            allQuestions.forEach { question ->
                println("Q${question.id} answers: ${question.answers.map { "A${it.id}: is_correct=${it.is_correct}" }}")
            }

            val validQuestions = allQuestions.filter { question ->
                val hasValidAnswer = question.answers.any { it.is_correct }
                val hasAnswers = question.answers.isNotEmpty()
                val isValidType = question.type_test == "liet" || question.type_test == "thuong"
                if (!hasAnswers) {
                    println("Câu hỏi Q${question.id} không có đáp án")
                } else if (!hasValidAnswer) {
                    println("Câu hỏi Q${question.id} không có đáp án đúng (is_correct = true)")
                } else if (!isValidType) {
                    println("Câu hỏi Q${question.id} có type_test không hợp lệ: ${question.type_test}")
                }
                question.answers.forEach { answer ->
                    println("Q${question.id} A${answer.id}: is_correct=${answer.is_correct}")
                }
                hasAnswers && hasValidAnswer && isValidType
            }
            println("Số câu hỏi hợp lệ: ${validQuestions.size}")

            val lietCount = validQuestions.count { it.type_test == "liet" }
            val thuongCount = validQuestions.count { it.type_test == "thuong" }
            println("Câu hỏi liet: $lietCount, Câu hỏi thuong: $thuongCount")

            if (validQuestions.size < 25 || lietCount < 1 || thuongCount < 24) {
                println("Cảnh báo: Không đủ câu hỏi hợp lệ (Cần 1 liet, 24 thuong)")
                _examQuestions.value = emptyList()
                _errorMessage.value = "Không đủ câu hỏi hợp lệ trong Firebase!"
                return@launch
            }

            val lietQuestion = validQuestions.filter { it.type_test == "liet" }.randomOrNull()
            val thuongQuestions = validQuestions.filter { it.type_test == "thuong" }.shuffled().take(24)
            println("Câu liệt: ${lietQuestion?.id}, Câu thường: ${thuongQuestions.size}")

            val selectedQuestions = buildList {
                lietQuestion?.let { add(it) }
                addAll(thuongQuestions)
            }.take(25)
            println("Đã chọn ${selectedQuestions.size} câu hỏi: ${selectedQuestions.map { it.id }}")
            _examQuestions.value = selectedQuestions
            _errorMessage.value = null
        } catch (e: Exception) {
            println("Lỗi khi tải câu hỏi: ${e.message}")
            _examQuestions.value = emptyList()
            _errorMessage.value = "Lỗi tải câu hỏi: ${e.message}"
        }
    }

    fun selectAnswer(questionId: Int, answerId: Int, context: Context) = viewModelScope.launch {
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            this[questionId] = answerId
        }
        _isAnswerSelected.value = _isAnswerSelected.value.toMutableMap().apply {
            this[questionId] = true
        }
        val isCorrect = checkAnswer(questionId, answerId, context)
        val correctAnswer = _examQuestions.value.find { it.id == questionId }
            ?.answers?.find { it.is_correct }?.answer_text
        _answerResults.value = _answerResults.value.toMutableMap().apply {
            this[questionId] = AnswerResult(
                isCorrect = isCorrect,
                correctAnswerText = correctAnswer
            )
        }
        println("Chọn đáp án Q$questionId: A$answerId, Đúng=$isCorrect, Đáp án đúng=$correctAnswer, Kết quả: ${_answerResults.value}")
    }

    suspend fun checkAnswer(questionId: Int, answerId: Int, context: Context): Boolean {
        if (!isNetworkAvailable(context)) {
            println("Không có kết nối mạng, kiểm tra cục bộ")
            return _examQuestions.value.find { it.id == questionId }
                ?.answers?.find { it.id == answerId }?.is_correct ?: false
        }

        val question = _examQuestions.value.find { it.id == questionId }
        if (question != null) {
            val isCorrect = question.answers.find { it.id == answerId }?.is_correct ?: false
            println("Kiểm tra cục bộ Q$questionId A$answerId: is_correct=$isCorrect")
            return isCorrect
        }

        return try {
            val questionRef = FirebaseClient.database.getReference("questions/$questionId/answers")
            val snapshot = withContext(Dispatchers.IO) { questionRef.get().await() }
            println("Firebase snapshot for Q$questionId: ${snapshot.value}")

            if (!snapshot.exists()) {
                println("Không tìm thấy đáp án cho Q$questionId")
                return false
            }

            val answers = snapshot.getValue(object : GenericTypeIndicator<List<Map<String, Any>>>() {})
            if (answers == null) {
                println("Dữ liệu đáp án rỗng hoặc không ánh xạ được")
                return false
            }

            val selectedAnswer = answers.firstOrNull {
                val id = when (val idValue = it["id"]) {
                    is Long -> idValue.toInt()
                    is Int -> idValue
                    is String -> idValue.toIntOrNull()
                    else -> null
                }
                id == answerId
            }

            if (selectedAnswer == null) {
                println("Không tìm thấy đáp án với id=$answerId")
                return false
            }

            val isCorrect = when (val isCorrectValue = selectedAnswer["is_correct"]) {
                is Boolean -> isCorrectValue
                is String -> isCorrectValue.toBoolean()
                else -> false
            }
            println("Kiểm tra Firebase Q$questionId A$answerId: is_correct=$isCorrect, answers=$answers")
            isCorrect
        } catch (e: Exception) {
            println("Lỗi kiểm tra đáp án: ${e.message}")
            return false
        }
    }

    fun submitExam(onResult: (score: Int, hasPassed: Boolean) -> Unit) = viewModelScope.launch {
        val questions = _examQuestions.value
        val answers = _selectedAnswers.value
        println("Nộp bài: ${questions.size} câu hỏi, ${answers.size} đáp án đã chọn")

        var score = 0
        var hasFailedLiet = false

        val results = questions.associate { question ->
            val correctAnswerId = question.answers.find { it.is_correct }?.id
            val correctAnswerText = question.answers.find { it.is_correct }?.answer_text
            val userAnswerId = answers[question.id]
            println("Q${question.id}: Đáp án đúng=$correctAnswerId, Đáp án người dùng=$userAnswerId, Loại=${question.type_test}")

            if (correctAnswerId == null) {
                println("Lỗi: Q${question.id} không có đáp án đúng")
                question.id to AnswerResult(isCorrect = false, correctAnswerText = correctAnswerText)
            } else {
                val isCorrect = userAnswerId != null && userAnswerId == correctAnswerId
                if (isCorrect) {
                    score++
                    println("Đúng Q${question.id}, Điểm: $score")
                } else if (question.type_test == "liet") {
                    hasFailedLiet = true
                    println("Sai câu liệt Q${question.id}")
                }
                question.id to AnswerResult(isCorrect = isCorrect, correctAnswerText = correctAnswerText)
            }
        }

        _answerResults.value = results
        _score.value = score
        _hasFailedLiet.value = hasFailedLiet
        _isExamSubmitted.value = true
        println("Điểm cuối cùng: $score, Có sai câu liệt: $hasFailedLiet")
        val hasPassed = !hasFailedLiet && score >= 23

        onResult(score, hasPassed)
    }

    fun startTimer(onTimeout: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        while (_timeRemaining.value > 0 && !_isExamSubmitted.value) {
            kotlinx.coroutines.delay(1000)
            _timeRemaining.value = _timeRemaining.value - 1
        }
        if (!_isExamSubmitted.value) {
            println("Hết giờ, nộp bài")
            onTimeout()
        }
    }

    fun resetExam() {
        _examQuestions.value = emptyList()
        _selectedAnswers.value = emptyMap()
        _answerResults.value = emptyMap()
        _isAnswerSelected.value = emptyMap()
        _timeRemaining.value = 19 * 60
        _isExamSubmitted.value = false
        _score.value = 0
        _hasFailedLiet.value = false
        _errorMessage.value = null
        println("Đã reset bài thi")
    }
}