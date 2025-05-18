package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_tblxa1.data.bienBaoCam
import com.example.app_tblxa1.model.TrafficSign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrafficViewModel : ViewModel() {
    private val _trafficSigns = MutableStateFlow<List<TrafficSign>>(bienBaoCam)
    val trafficSigns: StateFlow<List<TrafficSign>> = _trafficSigns

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        // Dữ liệu đã fix cứng, không cần fetch
        _trafficSigns.value = bienBaoCam
    }
}