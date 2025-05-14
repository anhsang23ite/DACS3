package com.example.app_tblxa1.repository

import com.example.app_tblxa1.model.Question
import com.example.app_tblxa1.database.FirebaseClient
import kotlinx.coroutines.tasks.await

class QuestionRepository {

    private val questionRef = FirebaseClient.database.getReference("questions")

    suspend fun getQuestions(): List<Question> {
        return try {
            val questions = mutableListOf<Question>()
            val dataSnapshot = questionRef.get().await() // Await Firebase request
            for (child in dataSnapshot.children) {
                val question = child.getValue(Question::class.java)
                println("Fetched Question: $question") // Log để kiểm tra dữ liệu
                question?.let { questions.add(it) }
            }
            questions
        } catch (e: Exception) {
            println("Error fetching questions: ${e.message}")
            emptyList() // Trả về danh sách rỗng nếu có lỗi
        }
    }
}