package com.example.app_tblxa1.ui.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExamCard(
    questionCount: String,
    examNumber: String,
    duration: String,
    modifier: Modifier = Modifier,
    onStartExam: () -> Unit // Thêm callback
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(width = 1.dp, color = Color.Blue, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = questionCount,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = examNumber,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = duration,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Button(
                onClick = onStartExam, // Sử dụng onStartExam
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Blue)
            ) {
                Text(text = "Bắt đầu", color = Color.Blue)
            }
        }
    }
}