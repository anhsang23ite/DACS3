package com.example.app_tblxa1.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.app_tblxa1.model.AuthRepository
import com.example.app_tblxa1.model.User
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val isPasswordVisibility = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val authRepository = AuthRepository()

    fun onSignInClick(navController: NavController) {
        val user = User(email = email.value, password = password.value)

        isLoading.value = true
        errorMessage.value = null

        viewModelScope.launch {
            authRepository.signIn(user,
                onSuccess = {
                    isLoading.value = false
                    navController.navigate("main") {
                        popUpTo("sign_in") { inclusive = true } // Xóa stack trước đó
                    }
                },
                onFailure = { exception ->
                    isLoading.value = false
                    errorMessage.value = exception.message ?: "Unknown error"
                    Log.e("SignInViewModel", "Sign-in failed: ${exception.message}")
                }
            )
        }
    }
}