package com.example.app_tblxa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app_tblxa1.R
import com.example.app_tblxa1.ui.theme.components.*
import com.example.app_tblxa1.viewmodel.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = SignUpViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TitleSignUp()
        FormSignUp(
            navController = navController,
            signUpViewModel = signUpViewModel
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlreadyLink(navController)
        Text(
            text = "Or",
            color = colorResource(id = R.color.gray),
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        GoogleAuth()
    }
}

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    MealtimeAppTheme {
        SignUpScreen(
            navController = rememberNavController(),
            signUpViewModel = SignUpViewModel()
        )
    }
}*/