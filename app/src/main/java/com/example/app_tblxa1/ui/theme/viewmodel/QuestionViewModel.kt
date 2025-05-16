package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_tblxa1.database.FirebaseClient
import com.example.app_tblxa1.model.Questions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    suspend fun checkAnswer(questionId: Int, answerId: Int): Boolean {
        return try {
            val questionRef = FirebaseClient.database.getReference("questions/$questionId")
            val snapshot = withContext(Dispatchers.IO) { questionRef.get().await() }
            println("Snapshot for question $questionId: ${snapshot.value}")
            val answers = snapshot.child("answers").children
            for (answer in answers) {
                val answerIdFromFirebase = answer.child("id").getValue(Int::class.java)
                val rawIsCorrect = answer.child("is_correct").value // Lấy giá trị thô
                val isCorrect = answer.child("is_correct").getValue(Boolean::class.java) ?: false
                println("Answer ID: $answerIdFromFirebase, Raw is_correct: $rawIsCorrect, Parsed is_correct: $isCorrect")
                if (answerIdFromFirebase == answerId) {
                    return isCorrect
                }
            }
            false
        } catch (e: Exception) {
            println("Error checking answer: ${e.message}")
            false
        }
    }
    fun selectAnswer(questionId: Int, answerId: Int) {
        val question = _questions.value.find { it.id == questionId }
        val isCorrect = question?.answers?.find { it.id == answerId }?.is_correct ?: false

        val updatedResults = _answerResults.value.toMutableMap()
        updatedResults[questionId] = isCorrect
        _answerResults.value = updatedResults
    }
}