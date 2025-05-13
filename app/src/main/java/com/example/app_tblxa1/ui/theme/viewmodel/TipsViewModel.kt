package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.model.Tips
import com.example.app_tblxa1.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TipsViewModel : ViewModel() {
    private val _tipsList = MutableStateFlow<List<Tips>>(emptyList())
    val tipsList: StateFlow<List<Tips>> = _tipsList

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchTips() {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getTips()
                _tipsList.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
