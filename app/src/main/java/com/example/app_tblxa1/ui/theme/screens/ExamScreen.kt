package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.ui.theme.components.ExamCard

@Composable
fun ExamScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(8) { index ->
            ExamCard(
                questionCount = "25 câu hỏi",
                examNumber = "Đề số ${index + 1}",
                duration = "Thời gian 19 phút",
                onStartExam = { /* Thêm logic bắt đầu thi */ }
            )
        }
    }
}