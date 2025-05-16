package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.app_tblxa1.model.Answer
import com.example.app_tblxa1.model.AnswerResult
import com.example.app_tblxa1.model.Question
import com.example.app_tblxa1.model.wrongQuestions

class WrongQuestionViewModel : ViewModel() {
    private val _questions = mutableStateOf<List<Question>>(emptyList())
    val questions: State<List<Question>> = _questions

    private val _answerResults = mutableStateOf<Map<Int, Boolean?>>(emptyMap())
    val answerResults: State<Map<Int, Boolean?>> = _answerResults

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        // Initialize with static data
        _questions.value = wrongQuestions
    }

    fun checkAnswer(questionId: Int, selectedAnswerId: Int): AnswerResult {
        val question = _questions.value.find { it.id1 == questionId }
        val selectedAnswer = question?.answer1?.find { it.id1 == selectedAnswerId }
        val isCorrect = selectedAnswer?.is_correct1 ?: false
        val correctAnswerText = question?.answer1?.find { it.is_correct1 }?.answer_text1
        val updatedResults = _answerResults.value.toMutableMap()
        updatedResults[questionId] = isCorrect
        _answerResults.value = updatedResults
        return AnswerResult(isCorrect, correctAnswerText)
    }
}