package com.example.app_tblxa1.model

data class Question(
    val id: Int = 0,
    val question_text: String = "",
    val image_url: String? = null,
    val type_test: String = "",
    val type_learn: String = "",
    val answers: List<Answer> = emptyList()
)

