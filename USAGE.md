# 喝水提醒应用 - 使用说明

## 快速开始

### 方法一：使用Android Studio（推荐）

1. **安装Android Studio**
   - 下载地址：https://developer.android.com/studio
   - 安装时选择"Standard"标准安装
   - 安装后会自动下载Android SDK

2. **打开项目**
   - 启动Android Studio
   - 选择 "Open an Existing Project"
   - 导航到 `c:\drink_water` 文件夹
   - 等待Gradle同步完成（首次需要几分钟）

3. **配置local.properties**
   - 如果Android Studio自动配置成功，跳过此步
   - 手动配置：打开 `c:\drink_water\local.properties`
   - 将 `YourUsername` 替换为你的Windows用户名
   - 例如：`C:\\Users\\zhangsan\\AppData\\Local\\Android\\Sdk`

4. **运行应用**
   - 连接Android手机（开启USB调试）或启动模拟器
   - 点击工具栏的绿色三角形 ▶️ Run按钮
   - 应用将自动安装到设备

5. **使用Android模拟器（无需真机）**
   - 在Android Studio中点击 "Device Manager"
   - 点击 "Create Device"
   - 选择一个设备（如 Pixel 6）
   - 下载系统镜像
   - 启动模拟器后点击Run

### 方法二：命令行构建APK

1. **安装必要工具**
   - 安装JDK 17或更高版本
   - 安装Android SDK（通过Android Studio或命令行工具）

2. **配置环境变量**
   - 设置 `JAVA_HOME` 指向JDK安装目录
   - 将 `%JAVA_HOME%\bin` 添加到PATH
   - 设置 `ANDROID_HOME` 指向SDK安装目录
   - 将 `%ANDROID_HOME%\platform-tools` 添加到PATH

3. **构建APK**
   ```bash
   cd c:\drink_water
   gradlew.bat assembleDebug
   ```

4. **获取APK文件**
   - 构建完成后，APK位于：
   - `c:\drink_water\app\build\outputs\apk\debug\app-debug.apk`

5. **安装到手机**
   - 将APK文件发送到手机
   - 在手机上点击安装
   - 允许安装未知来源的应用

## 应用使用指南

### 首次使用

1. **授予通知权限**
   - 首次打开应用时，会请求通知权限
   - 点击"允许"以接收喝水提醒

2. **记录喝水**
   - 点击主界面中央的大按钮"我喝了一杯水"
   - 今日喝水次数会立即更新

3. **设置提醒**
   - 点击右上角的齿轮图标 ⚙️
   - 开启/关闭提醒功能
   - 调整提醒间隔（15分钟到180分钟）
   - 设置提醒时间段（开始时间和结束时间）

### 功能说明

- **今日喝水次数**：显示今天喝了多少杯水
- **最近记录**：查看最近10次喝水记录及时间
- **智能提醒**：只在设定时间段内提醒，避免打扰休息
- **系统通知**：收到通知会显示"张昕，该喝水啦！💧"

## 常见问题

### Q: 提示"JAVA_HOME is not set"
A: 需要安装JDK并设置JAVA_HOME环境变量，建议直接安装Android Studio，它会自动处理这些配置。

### Q: Gradle同步失败
A: 
- 检查网络连接
- 在Android Studio中点击 "File" -> "Invalidate Caches / Restart"
- 删除 `.gradle` 文件夹后重新同步

### Q: 找不到Android SDK
A:
- 打开 Android Studio 的 SDK Manager
- 确保安装了 Android SDK Platform-Tools 和 Android SDK Build-Tools
- 更新 `local.properties` 中的 `sdk.dir` 路径

### Q: 模拟器运行很慢
A:
- 在AVD Manager中启用"Hardware Acceleration"
- 确保电脑开启了虚拟化技术（Intel VT-x 或 AMD-V）
- 选择较低的Android版本（如API 30）

### Q: 应用安装后没有通知
A:
- 检查手机的通知权限设置
- 确保没有在电池优化中禁用该应用
- 在应用设置中确认提醒功能已开启

## 系统要求

### 开发环境
- Windows 10/11
- JDK 17 或更高版本
- Android SDK 34
- 至少 8GB RAM
- 至少 10GB 可用磁盘空间

### 运行设备
- Android 7.0 (API 24) 或更高版本
- 100MB 可用存储空间

## 技术支持

如遇到问题，请检查：
1. Android Studio和SDK是否为最新版本
2. 是否有足够的磁盘空间
3. 网络连接是否正常
4. 杀毒软件是否阻止了Gradle下载

## 快捷键（Android Studio）

- `Ctrl + F9`：构建项目
- `Shift + F10`：运行应用
- `Ctrl + S`：保存文件
- `Ctrl + Alt + S`：打开设置
- `Alt + 1`：打开项目视图

## 提示

- 首次构建会下载Gradle依赖，需要几分钟
- 建议使用WiFi网络，避免使用流量
- 可以在应用设置中自定义张昕的名字
- 建议将应用加入电池优化白名单
