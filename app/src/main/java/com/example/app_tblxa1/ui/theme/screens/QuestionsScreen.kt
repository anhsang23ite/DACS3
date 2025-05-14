package com.example.app_tblxa1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_tblxa1.ui.theme.components.QuestionCard
import com.example.app_tblxa1.viewmodel.QuestionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionsScreen(
    navController: NavHostController,
    category: String,
    viewModel: QuestionViewModel = viewModel()
) {
    val questions = viewModel.questions.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // Trigger fetchQuestions when the screen is displayed
    LaunchedEffect(category) {
        println("Starting fetch for category: $category") // Log để kiểm tra
        viewModel.fetchQuestions(category)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Danh sách câu hỏi: $category", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
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
                    .padding(16.dp)
            ) {
                when {
                    errorMessage.value != null -> {
                        Text(
                            text = "Lỗi: ${errorMessage.value}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    questions.value.isEmpty() -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(questions.value) { question ->
                                QuestionCard(question = question.question_text)
                            }
                        }
                    }
                }
            }
        }
    )
}