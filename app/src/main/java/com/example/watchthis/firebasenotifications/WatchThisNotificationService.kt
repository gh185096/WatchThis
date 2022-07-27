package com.example.watchthis.firebasenotifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.watchthis.MainActivity
import com.example.watchthis.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class WatchThisNotificationService : FirebaseMessagingService() {
    private val firestoreDB = FirebaseFirestore.getInstance()

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(
                TAG, "Message Notification Body: " + remoteMessage.notification!!
                    .body
            )
        }
        sendNotification(remoteMessage)
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationTokenToFirestore(token)
    }

    private fun sendRegistrationTokenToFirestore(token: String) {
        val deviceToken: MutableMap<String, Any> = HashMap()
        deviceToken["Token"] = token
        val ref = firestoreDB.collection("cloudNotificationDeviceTokens")
            .document(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot.exists()) {
                    ref.update("Token", token)
                }
            } else {
                ref.set(deviceToken)
                    .addOnSuccessListener {
                        Log.d(
                            TAG,
                            "Token uploaded to Firestore"
                        )
                    }
                    .addOnFailureListener { error: Exception? ->
                        Log.e(
                            TAG,
                            "Token upload failed",
                            error
                        )
                    }
            }
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val channelId = "channel_1"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val broadcastIntent = Intent(this, NotificationBroadcastReceiver::class.java).apply {
            putExtra("documentId", remoteMessage.notification?.body)
        }

        val broadcastPendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, broadcastIntent, PendingIntent.FLAG_IMMUTABLE)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.notification?.title)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_launcher_foreground, "Accept", broadcastPendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        const val TAG = "BESTMESSAGE"
    }
}