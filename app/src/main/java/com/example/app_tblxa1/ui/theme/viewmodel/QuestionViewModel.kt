package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.database.FirebaseClient
import com.example.app_tblxa1.model.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchQuestions(category: String) {
        println("Fetching questions for category: $category") // Log để kiểm tra giá trị category
        val query = FirebaseClient.database
            .getReference("questions")
            .orderByChild("type_learn")
            .equalTo(category)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    println("Snapshot exists: ${snapshot.exists()}, children count: ${snapshot.childrenCount}") // Log để kiểm tra snapshot
                    val questionList = mutableListOf<Question>()
                    for (child in snapshot.children) {
                        val question = child.getValue(Question::class.java)
                        println("Question: $question") // Log từng question
                        question?.let { questionList.add(it) }
                    }
                    _questions.value = questionList
                } catch (e: Exception) {
                    println("Error mapping question: ${e.message}")
                    _errorMessage.value = "Error parsing data: ${e.message}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase error: ${error.message}")
                _errorMessage.value = "Firebase error: ${error.message}"
            }
        })
    }
}