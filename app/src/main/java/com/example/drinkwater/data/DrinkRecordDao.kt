package com.example.drinkwater.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DrinkRecordDao {
    @Insert
    suspend fun insert(record: DrinkRecord)

    @Query("SELECT * FROM drink_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<DrinkRecord>>

    @Query("SELECT COUNT(*) FROM drink_records WHERE timestamp >= :startTime")
    suspend fun getTodayCount(startTime: Long): Int

    @Query("SELECT * FROM drink_records ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentRecords(limit: Int): Flow<List<DrinkRecord>>

    @Query("DELETE FROM drink_records")
    suspend fun deleteAll()
}
