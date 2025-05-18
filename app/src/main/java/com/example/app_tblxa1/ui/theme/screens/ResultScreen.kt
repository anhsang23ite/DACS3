package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.viewmodel.ExamViewModel

@Composable
fun ResultScreen(
    navController: NavHostController,
    examViewModel: ExamViewModel
) {
    val score = examViewModel.score.collectAsState().value
    val hasFailedLiet = examViewModel.hasFailedLiet.collectAsState().value
    val hasPassed = !hasFailedLiet && score >= 23

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kết quả: $score/25",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (hasFailedLiet) {
            Text(
                text = "Trượt do sai câu điểm liệt!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else if (!hasPassed) {
            Text(
                text = "Trượt do điểm dưới 23!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        } else {
            Text(
                text = "Đậu! Chúc mừng bạn!",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                examViewModel.resetExam()
                navController.navigate("home")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Quay lại trang chủ")
        }

        Button(
            onClick = {
                navController.navigate("review")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Xem lại bài thi")
        }
    }
}