package com.example.drinkwater.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [DrinkRecord::class, ReminderSettings::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drinkRecordDao(): DrinkRecordDao
    abstract fun reminderSettingsDao(): ReminderSettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "drink_water_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
