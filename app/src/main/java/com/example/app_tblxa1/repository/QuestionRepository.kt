package com.example.app_tblxa1.repository

import com.example.app_tblxa1.model.Questions
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class QuestionRepository {

    private val questionRef = Firebase.database.getReference("questions")

    suspend fun getQuestions(): List<Questions> {
        return try {
            val questions = mutableListOf<Questions>()
            val dataSnapshot = questionRef.get().await()

            println("DataSnapshot: ${dataSnapshot.value}")
            if (!dataSnapshot.exists()) {
                println("Node 'questions' does not exist or is empty")
            }
            for (child in dataSnapshot.children) {
                val question = child.getValue(Questions::class.java)
                println("Fetched Question: $question")
                question?.let { questions.add(it) }
                    ?: println("Null Question for child: ${child.key}, data: ${child.value}")
            }
            println("Final Questions List: $questions")
            questions
        } catch (e: Exception) {
            println("Error fetching questions: ${e.message}")
            emptyList()
        }
    }
}