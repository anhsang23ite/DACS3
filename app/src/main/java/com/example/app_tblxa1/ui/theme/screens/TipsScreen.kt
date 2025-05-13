package com.example.app_tblxa1.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.app_tblxa1.ui.theme.components.TipCard
import com.example.app_tblxa1.viewmodel.TipsViewModel

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipsScreen(
    navController: NavHostController,
    viewModel: TipsViewModel = viewModel()
) {
    val tipsList = viewModel.tipsList.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // Fetch tips when the screen is loaded
    remember { viewModel.fetchTips() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mẹo Thi", color = Color.Black) },
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
                    .padding(16.dp)
            ) {
                if (errorMessage.value != null) {
                    Text(
                        text = "Lỗi: ${errorMessage.value}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                } else if (tipsList.value.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(tipsList.value) { tip ->
                            TipCard(section = tip)
                        }
                    }
                }
            }
        }
    )
}