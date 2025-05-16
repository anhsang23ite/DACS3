package com.example.app_tblxa1.model

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth

data class User(
    val email: String = "",
    val password: String = "",
)

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$".toRegex()
        return password.matches(passwordRegex)
    }

    // Đăng ký tài khoản (nếu cần)
    fun signUp(user: User, confirmPassword: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (!isValidEmail(user.email) || !isValidPassword(user.password) || user.password != confirmPassword) {
            onFailure(Exception("Thông tin không hợp lệ"))
            return
        }

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Lỗi không xác định"))
                }
            }
    }

    // Đăng nhập bằng Firebase
    fun signIn(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        if (!isValidEmail(user.email) || !isValidPassword(user.password)) {
            onFailure(Exception("Email hoặc mật khẩu không hợp lệ"))
            return
        }

        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Lỗi đăng nhập"))
                }
            }
    }

    fun forgotPassword(email: String, onComplete: (Boolean, String?) -> Unit) {
        if (!isValidEmail(email)) {
            onComplete(false, "Email không hợp lệ")
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
}