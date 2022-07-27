package com.example.watchthis.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchthis.sharedpreferences.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    val currentPIN = MutableLiveData(sessionManager.getPIN())

    fun updatePIN(newPin: String) {
        sessionManager.savePIN(newPin)
        currentPIN.value = sessionManager.getPIN()
    }
}