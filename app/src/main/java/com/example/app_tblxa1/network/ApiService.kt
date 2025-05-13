package com.example.app_tblxa1.network

import com.example.app_tblxa1.model.Question
import com.example.app_tblxa1.model.Tips
import com.example.app_tblxa1.model.TrafficSign
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("driving_theory_app.php")
    suspend fun getQuestions(
        @Query("endpoint") endpoint: String = "questions",
        @Query("category") category: String
    ): List<Question>

    @GET("driving_theory_app.php")
    suspend fun getTips(
        @Query("endpoint") endpoint: String = "tips"
    ): List<Tips>

    @GET("driving_theory_app.php")
    suspend fun getTrafficSigns(
        @Query("endpoint") endpoint: String = "traffic_signs"
    ): List<TrafficSign>
}
