package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.model.TrafficSign
import com.example.app_tblxa1.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrafficViewModel : ViewModel() {
    private val _trafficSigns = MutableStateFlow<List<TrafficSign>>(emptyList())
    val trafficSigns: StateFlow<List<TrafficSign>> = _trafficSigns

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchTrafficSigns() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getTrafficSigns()
                _trafficSigns.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
