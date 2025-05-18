package com.example.app_tblxa1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_tblxa1.R
import com.example.app_tblxa1.ui.theme.components.TrafficCard
import com.example.app_tblxa1.viewmodel.TrafficViewModel

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrafficScreen(
    navController: NavHostController,
    viewModel: TrafficViewModel = viewModel()
) {
    val trafficSigns = viewModel.trafficSigns.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // State để theo dõi flashcard hiện tại
    var currentIndex by remember { mutableStateOf(0) }
    val signsList = trafficSigns.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Biển Báo Giao Thông", color = Color.Black) },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (errorMessage.value != null) {
                    Text(
                        text = "Lỗi: ${errorMessage.value}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (signsList.isEmpty()) {
                    Text(
                        text = "Không có dữ liệu",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    // Hiển thị flashcard hiện tại
                    Spacer(modifier = Modifier.weight(1f))
                    TrafficCard(
                        trafficSign = signsList[currentIndex],
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    // Hiển thị số thứ tự
                    Text(
                        text = "${currentIndex + 1}/${signsList.size}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Nút điều hướng
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = {
                                if (currentIndex > 0) {
                                    currentIndex--
                                }
                            },
                            enabled = currentIndex > 0
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.left),
                                contentDescription = "Previous",
                                modifier = Modifier.size(24.dp),
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                    if (currentIndex > 0) Color.Green else Color.Gray
                                )
                            )
                        }

                        IconButton(
                            onClick = {
                                if (currentIndex < signsList.size - 1) {
                                    currentIndex++
                                }
                            },
                            enabled = currentIndex < signsList.size - 1
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.right),
                                contentDescription = "Next",
                                modifier = Modifier.size(24.dp),
                                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                    if (currentIndex < signsList.size - 1) Color.Green else Color.Gray
                                )
                            )
                        }
                    }
                }
            }
        }
    )
}