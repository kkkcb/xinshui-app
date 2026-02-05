package com.example.drinkwater.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.drinkwater.data.AppDatabase
import com.example.drinkwater.data.ReminderSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ReminderScheduler(private val context: Context) {

    fun scheduleReminder() {
        CoroutineScope(Dispatchers.IO).launch {
            val settings = getReminderSettings() ?: return@launch
            
            if (settings.isEnabled) {
                scheduleWork(settings.intervalMinutes)
            } else {
                cancelWork()
            }
        }
    }

    fun scheduleReminderSync(settings: ReminderSettings) {
        if (settings.isEnabled) {
            scheduleWork(settings.intervalMinutes)
        } else {
            cancelWork()
        }
    }

    private fun scheduleWork(intervalMinutes: Int) {
        val constraints = Constraints.Builder()
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            intervalMinutes.toLong(),
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "drink_water_reminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    private fun cancelWork() {
        WorkManager.getInstance(context).cancelUniqueWork("drink_water_reminder")
    }

    private suspend fun getReminderSettings(): ReminderSettings? {
        return AppDatabase.getDatabase(context).reminderSettingsDao().getSettingsSync()
    }

    companion object {
        fun getInstance(context: Context): ReminderScheduler {
            return ReminderScheduler(context.applicationContext)
        }
    }
}
