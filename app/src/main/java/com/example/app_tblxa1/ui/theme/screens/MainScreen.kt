package com.example.app_tblxa1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.R
import com.example.app_tblxa1.components.CardItem

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "GPLX Hạng A1",
                color = Color(0xFF2563EB),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color(0xFF2563EB),
                modifier = Modifier.size(28.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.banner_image),
            contentDescription = "Banner Image",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardItem(
                imageResId = R.drawable.book,
                text = "Học lý thuyết",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("theory")
            }
            CardItem(
                imageResId = R.drawable.directionscar,
                text = "Thi sát hạch",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("exam/thuong")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardItem(
                imageResId = R.drawable.traffic,
                text = "Biển báo",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("traffic_screen")
            }
            CardItem(
                imageResId = R.drawable.lightbulb,
                text = "Mẹo thi",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("tips_screen")
            }
            CardItem(
                imageResId = R.drawable.email,
                text = "Các câu sai",
                modifier = Modifier.weight(1f)
            ) {
                navController.navigate("wrong_questions") // Chuyển đến danh mục "Câu điểm liệt"
            }
        }
    }
}