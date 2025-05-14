package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.app_tblxa1.R
import com.example.app_tblxa1.ui.theme.components.TheoryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheoryScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Học Lý Thuyết", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TheoryCard(
                    imageResId = R.drawable.icon_concept,
                    title = "KHÁI NIỆM VÀ QUY TẮC",
                    subtitle = "Gồm 83 câu hỏi (18 Câu điểm liệt)",
                    progress = "0/83",
                    onClick = { navController.navigate("questions_screen/khai_niem") }
                )

                TheoryCard(
                    imageResId = R.drawable.icon_culture,
                    title = "VĂN HÓA VÀ ĐẠO ĐỨC LÁI",
                    subtitle = "Gồm 5 câu hỏi",
                    progress = "0/5",
                    onClick = { navController.navigate("questions_screen/van_hoa") }
                )

                TheoryCard(
                    imageResId = R.drawable.icon_driving,
                    title = "KỸ THUẬT LÁI XE",
                    subtitle = "Gồm 12 câu hỏi (2 Câu điểm liệt)",
                    progress = "0/12",
                    onClick = { navController.navigate("questions_screen/ki_thuat_lai") }
                )

                TheoryCard(
                    imageResId = R.drawable.icon_traffic_sign,
                    title = "BIỂN BÁO ĐƯỜNG BỘ",
                    subtitle = "Gồm 65 câu hỏi",
                    progress = "0/65",
                    onClick = { navController.navigate("questions_screen/bien_bao") }
                )

                TheoryCard(
                    imageResId = R.drawable.icon_practice,
                    title = "SA HÌNH",
                    subtitle = "Gồm 35 câu hỏi",
                    progress = "0/35",
                    onClick = { navController.navigate("questions_screen/sa_hinh") }
                )
            }
        }
    )
}