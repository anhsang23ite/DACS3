package com.example.app_tblxa1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_tblxa1.model.Tips
import com.example.app_tblxa1.database.FirebaseClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
                val tipsRef = FirebaseClient.database.getReference("tips")
                tipsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val tips = mutableListOf<Tips>()
                        for (child in snapshot.children) {
                            val tip = child.getValue(Tips::class.java)
                            tip?.let { tips.add(it) }
                        }
                        _tipsList.value = tips
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