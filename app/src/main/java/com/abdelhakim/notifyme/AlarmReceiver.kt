package com.abdelhakim.notifyme

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*


class AlarmReceiver : BroadcastReceiver() {

    /**
     * sends notification when receives alarm
     * and then reschedule the reminder again
     **/

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = context.getString(R.string.reminders_notification_channel_id)
        )
        Toast.makeText(context,"I worked", Toast.LENGTH_SHORT).show()

        var time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        var (hours, min) = time.split(":").map { it.toInt() }
        min += 29
        if (min > 59) {
            min -= 60
            if (hours+1 < 24) hours += 1  else hours = 0
        }
        time = "$hours:$min"
        // Remove this line if you don't want to reschedule the reminder
        RemindersManager.startReminder(context.applicationContext,time)
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {
    val flag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        flag
    )
    val builder = NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle(applicationContext.getString(R.string.title_notification_reminder))
        .setContentText(applicationContext.getString(R.string.description_notification_reminder))
        .setSmallIcon(R.drawable.ic_android)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(applicationContext.getString(R.string.description_notification_reminder))
        )
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

const val NOTIFICATION_ID = 1059
