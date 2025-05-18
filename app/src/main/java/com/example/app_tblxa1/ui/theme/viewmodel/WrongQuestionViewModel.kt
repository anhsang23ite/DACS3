package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.app_tblxa1.model.AnswerResult
import com.example.app_tblxa1.model.Questions
import com.example.app_tblxa1.model.wrongQuestions
import kotlin.collections.find

class WrongQuestionViewModel : ViewModel() {
    private val _questions = mutableStateOf<List<Questions>>(emptyList())
    val questions: State<List<Questions>> = _questions

    private val _answerResult = mutableStateOf<Map<Int, Boolean?>>(emptyMap())
    val answerResult: State<Map<Int, Boolean?>> = _answerResult

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        // Initialize with static data
        _questions.value = wrongQuestions
    }

    fun checkAnswer(questionId: Int, selectedAnswerId: Int): AnswerResult {
        val question = _questions.value.find { it.id == questionId }
        val selectedAnswer = question?.answers?.find { it.id == selectedAnswerId }
        val isCorrect = selectedAnswer?.is_correct ?: false
        val correctAnswerText = question?.answers?.find { it.is_correct }?.answer_text
        val updatedResults = _answerResult.value.toMutableMap()
        updatedResults[questionId] = isCorrect
        _answerResult.value = updatedResults
        return AnswerResult(isCorrect, correctAnswerText)
    }
}