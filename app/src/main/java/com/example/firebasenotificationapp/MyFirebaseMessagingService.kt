package com.example.firebasenotificationapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "Notification_channel"
const val channelName ="com.example.firebasenotificationapp"


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override  fun onMessageReceived(remoteMessage: RemoteMessage){
   if (remoteMessage.getNotification() != null)
   {
       generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
   }
    }
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String):RemoteViews {
        val remoteView = RemoteViews("com.example.firebasenotificationapp", R.layout.notification)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.icon,R.drawable.notification)
        return remoteView
    }
    fun generateNotification(title: String, message: String)
    {
      val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_ONE_SHOT)
         var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
             .setSmallIcon(R.drawable.notification)
             .setAutoCancel(true)
             .setVibrate(longArrayOf(1000,1000,1000))
             .setOnlyAlertOnce(true)
             .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            notificationManager.notify(0,builder.build())
    }

}