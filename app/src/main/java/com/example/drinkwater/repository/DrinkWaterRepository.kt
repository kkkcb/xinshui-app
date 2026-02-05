package com.example.drinkwater.repository

import com.example.drinkwater.data.DrinkRecord
import com.example.drinkwater.data.DrinkRecordDao
import com.example.drinkwater.data.ReminderSettings
import com.example.drinkwater.data.ReminderSettingsDao
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

class DrinkWaterRepository(
    private val drinkRecordDao: DrinkRecordDao,
    private val reminderSettingsDao: ReminderSettingsDao
) {
    fun getAllRecords(): Flow<List<DrinkRecord>> = drinkRecordDao.getAllRecords()

    fun getRecentRecords(limit: Int = 10): Flow<List<DrinkRecord>> = drinkRecordDao.getRecentRecords(limit)

    suspend fun getTodayCount(): Int {
        val startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return drinkRecordDao.getTodayCount(startOfDay)
    }

    suspend fun addDrinkRecord() {
        drinkRecordDao.insert(DrinkRecord())
    }

    suspend fun deleteAllRecords() {
        drinkRecordDao.deleteAll()
    }

    fun getReminderSettings(): Flow<ReminderSettings?> = reminderSettingsDao.getSettings()

    suspend fun getReminderSettingsSync(): ReminderSettings? = reminderSettingsDao.getSettingsSync()

    suspend fun saveReminderSettings(settings: ReminderSettings) {
        reminderSettingsDao.insert(settings)
    }
}
