package com.example.drinkwater

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.drinkwater.data.ReminderSettings
import com.example.drinkwater.ui.screens.MainScreen
import com.example.drinkwater.ui.screens.SettingsScreen
import com.example.drinkwater.ui.theme.DrinkWaterTheme
import com.example.drinkwater.viewmodel.DrinkWaterViewModel
import com.example.drinkwater.worker.ReminderScheduler

class MainActivity : ComponentActivity() {
    private val viewModel: DrinkWaterViewModel by viewModels()
    private lateinit var reminderScheduler: ReminderScheduler

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        reminderScheduler = ReminderScheduler.getInstance(this)
        
        requestNotificationPermission()
        
        reminderScheduler.scheduleReminder()

        setContent {
            DrinkWaterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val todayCount by viewModel.todayCount.collectAsState()
                    val recentRecords by viewModel.recentRecords.collectAsState()
                    val reminderSettings by viewModel.reminderSettings.collectAsState()
                    
                    var showSettings by remember { mutableStateOf(false) }
                    
                    if (showSettings) {
                        reminderSettings?.let { settings ->
                            SettingsScreen(
                                settings = settings,
                                onSave = { newSettings ->
                                    viewModel.saveReminderSettings(newSettings)
                                    reminderScheduler.scheduleReminderSync(newSettings)
                                    showSettings = false
                                },
                                onBackClick = { showSettings = false }
                            )
                        }
                    } else {
                        MainScreen(
                            todayCount = todayCount,
                            recentRecords = recentRecords,
                            reminderSettings = reminderSettings,
                            onDrinkClick = {
                                viewModel.onDrinkClick()
                            },
                            onSettingsClick = { showSettings = true }
                        )
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
