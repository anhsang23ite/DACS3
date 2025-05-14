package com.example.app_tblxa1.database

import com.google.firebase.database.FirebaseDatabase

object FirebaseClient {
    private const val DATABASE_URL = "https://drivingtheoryapi-default-rtdb.asia-southeast1.firebasedatabase.app/"
    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance(DATABASE_URL).apply {
            setPersistenceEnabled(true) // Bật lưu trữ cục bộ
        }
    }
}