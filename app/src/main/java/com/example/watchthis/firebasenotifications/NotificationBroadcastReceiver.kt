package com.example.watchthis.firebasenotifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.firebase.firestore.FirebaseFirestore

class NotificationBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val documentIdToUpdate = intent.getStringExtra("documentId")
            documentIdToUpdate?.let { document ->
                FirebaseFirestore.getInstance().collection("Merchants").document("Merchant 1")
                    .collection("Requests").document(document).update("requestMessage", "SOmETHINGNEW")
            }
        }
    }
}