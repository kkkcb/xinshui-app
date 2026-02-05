package com.example.drinkwater.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drinkwater.data.ReminderSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: ReminderSettings,
    onSave: (ReminderSettings) -> Unit,
    onBackClick: () -> Unit
) {
    var isEnabled by remember { mutableStateOf(settings.isEnabled) }
    var intervalMinutes by remember { mutableStateOf(settings.intervalMinutes) }
    var startHour by remember { mutableStateOf(settings.startHour) }
    var startMinute by remember { mutableStateOf(settings.startMinute) }
    var endHour by remember { mutableStateOf(settings.endHour) }
    var endMinute by remember { mutableStateOf(settings.endMinute) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "提醒设置", 
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "返回",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "启用提醒",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Switch(
                            checked = isEnabled,
                            onCheckedChange = { isEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                        )
                    }
                    
                    if (isEnabled) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "提醒间隔",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { 
                                    intervalMinutes = maxOf(15, intervalMinutes - 15)
                                }
                            ) {
                                Text("-15")
                            }
                            
                            Text(
                                text = "${intervalMinutes} 分钟",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            
                            OutlinedButton(
                                onClick = { 
                                    intervalMinutes = minOf(180, intervalMinutes + 15)
                                }
                            ) {
                                Text("+15")
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text(
                            text = "提醒时间段",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        TimePickerRow(
                            label = "开始时间",
                            hour = startHour,
                            minute = startMinute,
                            onHourChange = { startHour = it },
                            onMinuteChange = { startMinute = it }
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        TimePickerRow(
                            label = "结束时间",
                            hour = endHour,
                            minute = endMinute,
                            onHourChange = { endHour = it },
                            onMinuteChange = { endMinute = it }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = {
                    onSave(
                        ReminderSettings(
                            id = settings.id,
                            isEnabled = isEnabled,
                            intervalMinutes = intervalMinutes,
                            startHour = startHour,
                            startMinute = startMinute,
                            endHour = endHour,
                            endMinute = endMinute
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "保存设置",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TimePickerRow(
    label: String,
    hour: Int,
    minute: Int,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onHourChange(maxOf(0, hour - 1)) },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("-")
            }
            
            Text(
                text = "%02d".format(hour),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            
            OutlinedButton(
                onClick = { onHourChange(minOf(23, hour + 1)) },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("+")
            }
            
            Text(
                text = ":",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            
            OutlinedButton(
                onClick = { onMinuteChange(maxOf(0, minute - 1)) },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("-")
            }
            
            Text(
                text = "%02d".format(minute),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            
            OutlinedButton(
                onClick = { onMinuteChange(minOf(59, minute + 1)) },
                modifier = Modifier.size(40.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("+")
            }
        }
    }
}
