package com.example.watchthis

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.watchthis.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

val TAG = "MAIN"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var firestoreDB = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.d("NOTIFICATIONS", "Fetch Failed")
                    return@addOnCompleteListener
                }
                val token = task.result
                Log.d(TAG, token)
                val deviceToken: MutableMap<String, Any> = HashMap()
                deviceToken["Token"] = token
                val ref: DocumentReference = firestoreDB.collection("cloudNotificationDeviceTokens")
                    .document(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))
                ref.set(deviceToken)
                    .addOnSuccessListener { Log.d(TAG, "Token uploaded to Firestore") }
                    .addOnFailureListener { error: Exception? -> Log.e(TAG, "Token upload failed", error) }
            }
    }
}