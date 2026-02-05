package com.example.drinkwater.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkwater.data.DrinkRecord
import com.example.drinkwater.data.ReminderSettings
import com.example.drinkwater.repository.DrinkWaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DrinkWaterViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DrinkWaterRepository(
        com.example.drinkwater.data.AppDatabase.getDatabase(application).drinkRecordDao(),
        com.example.drinkwater.data.AppDatabase.getDatabase(application).reminderSettingsDao()
    )

    val todayCount = MutableStateFlow(0)
    val recentRecords = repository.getRecentRecords(10)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    private val _reminderSettings = MutableStateFlow<ReminderSettings?>(null)
    val reminderSettings = _reminderSettings.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            todayCount.value = repository.getTodayCount()
            repository.getReminderSettings().collect { settings ->
                _reminderSettings.value = settings
            }
        }
    }

    fun onDrinkClick() {
        viewModelScope.launch {
            repository.addDrinkRecord()
            todayCount.value = repository.getTodayCount()
        }
    }

    fun saveReminderSettings(settings: ReminderSettings) {
        viewModelScope.launch {
            repository.saveReminderSettings(settings)
        }
    }
}
