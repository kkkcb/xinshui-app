package com.example.drinkwater.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: ReminderSettings)

    @Query("SELECT * FROM reminder_settings WHERE id = 1")
    fun getSettings(): Flow<ReminderSettings?>

    @Query("SELECT * FROM reminder_settings WHERE id = 1")
    suspend fun getSettingsSync(): ReminderSettings?
}
