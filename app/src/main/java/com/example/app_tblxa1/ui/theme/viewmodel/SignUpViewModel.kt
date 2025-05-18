package com.example.app_tblxa1.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.app_tblxa1.model.AuthRepository
import com.example.app_tblxa1.model.User

class SignUpViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val isConfirmPasswordVisible = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val authRepository = AuthRepository()

    fun onSignUpClick(navController: NavController) {
        val user = User(email = email.value, password = password.value)

        if (password.value != confirmPassword.value) {
            errorMessage.value = "Passwords do not match"
            return
        }

        isLoading.value = true
        errorMessage.value = null

        authRepository.signUp(
            user = user,
            confirmPassword = confirmPassword.value,
            onSuccess = {
                isLoading.value = false
                navController.navigate("main") {
                    popUpTo("sign_up") { inclusive = true }
                }
            },
            onFailure = { exception ->
                isLoading.value = false
                errorMessage.value = exception.message ?: "Unknown error"
                Log.e("SignUpViewModel", "Sign-up failed: ${exception.message}")
            }
        )
    }
}