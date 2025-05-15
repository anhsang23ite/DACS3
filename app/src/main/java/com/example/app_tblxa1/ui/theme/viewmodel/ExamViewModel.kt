package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.database.FirebaseClient
import com.example.app_tblxa1.model.Questions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ExamViewModel : ViewModel() {
    private val _examQuestions = MutableStateFlow<List<Questions>>(emptyList())
    val examQuestions: StateFlow<List<Questions>> get() = _examQuestions

    private val _selectedAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedAnswers: StateFlow<Map<Int, Int>> get() = _selectedAnswers

    private val _timeRemaining = MutableStateFlow(19 * 60) // 19 phút
    val timeRemaining: StateFlow<Int> get() = _timeRemaining

    private val _isExamSubmitted = MutableStateFlow(false)
    val isExamSubmitted: StateFlow<Boolean> get() = _isExamSubmitted

    fun fetchExamQuestions() = viewModelScope.launch {
        try {
            val snapshot = FirebaseClient.database.getReference("questions").get().await()
            val allQuestions = snapshot.children.mapNotNull { it.getValue(Questions::class.java) }

            val lietQuestion = allQuestions.filter { it.type_test == "liet" }.randomOrNull()
            val thuongQuestions = allQuestions.filter { it.type_test == "thuong" }.shuffled().take(24)

            val selectedQuestions = buildList {
                lietQuestion?.let { add(it) }
                addAll(thuongQuestions)
            }

            _examQuestions.value = selectedQuestions
        } catch (e: Exception) {
            println("Error fetching exam questions: ${e.message}")
        }
    }

    fun selectAnswer(questionId: Int, answerId: Int) {
        _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
            this[questionId] = answerId
        }
    }

    fun submitExam(onResult: (score: Int, hasPassed: Boolean) -> Unit) = viewModelScope.launch {
        val questions = _examQuestions.value
        val answers = _selectedAnswers.value

        var score = 0
        var hasFailedLiet = false

        for (question in questions) {
            val correctAnswerId = question.answers.find { it.is_correct }?.id
            val userAnswerId = answers[question.id]

            if (userAnswerId != null && correctAnswerId != null && userAnswerId == correctAnswerId) {
                score++
            } else if (question.type_test == "liet") {
                hasFailedLiet = true
            }
        }

        _isExamSubmitted.value = true
        val hasPassed = !hasFailedLiet && score >= 23
        onResult(score, hasPassed)
    }

    fun startTimer(onTimeout: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        while (_timeRemaining.value > 0 && !_isExamSubmitted.value) {
            kotlinx.coroutines.delay(1000)
            _timeRemaining.value = _timeRemaining.value - 1
        }
        if (!_isExamSubmitted.value) onTimeout()
    }
}
