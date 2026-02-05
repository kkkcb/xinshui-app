package com.example.drinkwater.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_settings")
data class ReminderSettings(
    @PrimaryKey
    val id: Int = 1,
    val isEnabled: Boolean = true,
    val intervalMinutes: Int = 60,
    val startHour: Int = 9,
    val startMinute: Int = 0,
    val endHour: Int = 22,
    val endMinute: Int = 0
)
