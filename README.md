# 喝水提醒应用

这是一个为张昕设计的Android喝水提醒应用，使用Kotlin和Jetpack Compose开发。

## 功能特性

- **记录喝水次数**：点击按钮记录每次喝水
- **今日统计**：显示今日喝水次数
- **历史记录**：查看最近10次喝水记录
- **智能提醒**：可自定义提醒间隔和时间段
- **系统通知**：在设置的时间段内定时提醒喝水

## 技术栈

- **UI框架**: Jetpack Compose
- **语言**: Kotlin
- **架构**: MVVM
- **数据库**: Room
- **后台任务**: WorkManager
- **主题**: Material Design 3

## 项目结构

```
app/
├── src/main/
│   ├── java/com/example/drinkwater/
│   │   ├── data/           # 数据库实体和DAO
│   │   ├── repository/     # 数据仓库层
│   │   ├── viewmodel/      # ViewModel层
│   │   ├── worker/         # WorkManager后台任务
│   │   ├── ui/
│   │   │   ├── screens/    # UI界面
│   │   │   └── theme/      # 主题配置
│   │   └── MainActivity.kt
│   └── res/                # 资源文件
└── build.gradle.kts
```

## 如何使用

1. **安装应用**
   - 使用Android Studio打开项目
   - 连接Android设备或启动模拟器
   - 点击Run按钮安装应用

2. **记录喝水**
   - 在主界面点击"我喝了一杯水"按钮
   - 今日喝水次数会自动更新

3. **设置提醒**
   - 点击右上角设置图标
   - 启用/禁用提醒功能
   - 调整提醒间隔（15-180分钟）
   - 设置提醒时间段

## 提醒功能说明

- 应用使用WorkManager实现定时提醒
- 只在设置的时间段内发送通知
- 默认设置为：每小时提醒一次，9:00-22:00
- 通知会显示"张昕，该喝水啦！💧"

## 权限说明

应用需要以下权限：
- `POST_NOTIFICATIONS`：发送喝水提醒通知（Android 13+）
- `SCHEDULE_EXACT_ALARM`：设置精确的提醒时间
- `RECEIVE_BOOT_COMPLETED`：设备重启后恢复提醒

## 构建要求

- Android Studio Hedgehog (2023.1.1) 或更高版本
- JDK 17
- Android SDK 34
- Gradle 8.2

## 注意事项

- 首次运行时需要授予通知权限
- 建议将应用加入电池优化白名单，确保提醒功能正常工作
- 应用数据保存在本地，卸载后数据会丢失

## 未来改进

- [ ] 添加数据导出功能
- [ ] 支持多用户
- [ ] 添加喝水目标设置
- [ ] 统计图表展示
- [ ] 添加喝水提醒音效
- [ ] 云端数据同步
