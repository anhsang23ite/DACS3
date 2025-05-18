package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.app_tblxa1.model.wrongQuestions
import com.example.app_tblxa1.ui.theme.components.WrongQuestionCard

@Composable
fun WrongQuestionsScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(wrongQuestions) { index, question ->
            WrongQuestionCard(
                question = question,
                questionNumber = index + 1
            )
        }
    }
}