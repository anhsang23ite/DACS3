package com.example.app_tblxa1.model

data class Answers(
    val id: Int = 0,
    val answer_text: String = "",
    val is_correct: Boolean = false // Đảm bảo trường này được khai báo đúng kiểu Boolean
)