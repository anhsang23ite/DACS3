package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.model.TrafficSign
import com.example.app_tblxa1.database.FirebaseClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
                val trafficSignRef = FirebaseClient.database.getReference("traffic_signs")
                trafficSignRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val trafficSigns = mutableListOf<TrafficSign>()
                        for (child in snapshot.children) {
                            val trafficSign = child.getValue(TrafficSign::class.java)
                            trafficSign?.let { trafficSigns.add(it) }
                        }
                        _trafficSigns.value = trafficSigns
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _errorMessage.value = error.message
                    }
                })
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}