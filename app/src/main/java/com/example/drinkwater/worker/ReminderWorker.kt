package com.example.drinkwater.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.drinkwater.MainActivity
import com.example.drinkwater.R
import com.example.drinkwater.data.AppDatabase
import com.example.drinkwater.data.ReminderSettings
import kotlinx.coroutines.flow.first

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val settings = getReminderSettings() ?: return Result.success()
        
        if (!settings.isEnabled) {
            return Result.success()
        }

        val currentHour = java.time.LocalDateTime.now().hour
        val currentMinute = java.time.LocalDateTime.now().minute
        val currentTimeInMinutes = currentHour * 60 + currentMinute
        val startTimeInMinutes = settings.startHour * 60 + settings.startMinute
        val endTimeInMinutes = settings.endHour * 60 + settings.endMinute

        if (currentTimeInMinutes !in startTimeInMinutes..endTimeInMinutes) {
            return Result.success()
        }

        showNotification()
        return Result.success()
    }

    private suspend fun getReminderSettings(): ReminderSettings? {
        val settingsDao = AppDatabase.getDatabase(applicationContext).reminderSettingsDao()
        return settingsDao.getSettingsSync() ?: ReminderSettings(
            id = 1,
            isEnabled = true,
            intervalMinutes = 60,
            startHour = 9,
            startMinute = 0,
            endHour = 22,
            endMinute = 0
        )
    }

    private fun showNotification() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val channelId = "drink_water_channel"
        val channelName = "喝水提醒"
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "提醒张昕喝水"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(applicationContext.getString(R.string.notification_content))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(1, notification)
    }
}
