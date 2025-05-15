package com.example.app_tblxa1.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.R

@Composable
fun ExamResultScreen(score: Int, hasPassed: Boolean, navController: NavHostController) {
    val resultText = if (hasPassed) "Bạn đã ĐẬU 🎉" else "Bạn đã RỚT 😢"
    val resultColor = if (hasPassed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "KẾT QUẢ BÀI THI",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Điểm của bạn: $score / 25",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = resultText,
            style = MaterialTheme.typography.titleMedium.copy(
                color = resultColor,
                fontWeight = FontWeight.SemiBold
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("main") },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Quay về trang chính", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary))
        }
    }
}
