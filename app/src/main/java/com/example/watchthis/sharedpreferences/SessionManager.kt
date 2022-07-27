package com.example.watchthis.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.watchthis.R
import javax.inject.Inject

class SessionManager @Inject constructor(context: Context) {
    private var preferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val PIN = "manager_pin"
    }

    fun savePIN(pin: String) {
        val editor = preferences.edit()
        editor.putString(PIN, pin)
        editor.apply()
    }

    fun getPIN(): String {
        return preferences.getString(PIN, null) ?: ""
    }
}