# 心水 App 安卓 APK 打包指南

## 📱 项目信息

- **App 名称**: 心水
- **包名**: com.drinkwater.app
- **版本**: 1.0
- **类型**: 原生 Android 应用（Kotlin + Jetpack Compose）

## 🔧 环境要求

### 必须安装以下工具：

1. **JDK (Java Development Kit)**
   - 下载地址: https://adoptium.net/
   - 选择 JDK 17 或 JDK 21
   - 安装后设置环境变量 `JAVA_HOME`

2. **Android SDK**
   - 下载 Android Studio: https://developer.android.com/studio
   - 安装后配置 SDK 路径
   - 需要安装 Android SDK Build-Tools

3. **设置环境变量**
   ```bash
   # 添加到系统环境变量
   JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.9-hotspot
   ANDROID_HOME=C:\Users\YourName\AppData\Local\Android\Sdk
   ```

## 📦 打包步骤

### 方法一：使用命令行打包

1. **打开 PowerShell 或 CMD**

2. **进入项目目录**
   ```bash
   cd c:\drink_water
   ```

3. **构建 Debug APK**
   ```bash
   .\gradlew.bat assembleDebug
   ```

4. **查找 APK 文件**
   - 路径: `app\build\outputs\apk\debug\app-debug.apk`

### 方法二：使用 Android Studio 打包

1. **打开项目**
   - 启动 Android Studio
   - 选择 "Open an Existing Project"
   - 选择 `c:\drink_water` 目录

2. **构建 APK**
   - 菜单: Build > Build Bundle(s) / APK(s) > Build APK(s)

3. **查看 APK**
   - 构建完成后，点击 "locate" 按钮
   - 或手动打开: `app\build\outputs\apk\debug\app-debug.apk`

## 📲 安装 APK 到手机

### 1. **启用未知来源安装**
   - 设置 > 安全 > 允许安装未知来源应用

### 2. **传输 APK 到手机**
   - USB 数据线连接电脑
   - 或使用微信/QQ 等发送

### 3. **安装 APK**
   - 点击 APK 文件
   - 按提示完成安装

## ⚠️ 常见问题

### 1. JAVA_HOME 未设置
```bash
# Windows 设置命令（临时）
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
```

### 2. Gradle 构建失败
- 检查网络连接（需要下载依赖）
- 尝试清理缓存: `.\gradlew.bat clean`

### 3. SDK 路径错误
- 检查 `local.properties` 文件
- 确保 `sdk.dir` 指向正确的 Android SDK 路径

## 🎨 App 图标

当前使用默认的 Android 图标。

如需自定义图标：

1. 准备图标文件：
   - `ic_launcher.png` (192x192)
   - `ic_launcher_round.png` (192x192)

2. 放置到：
   - `app\src\main\res\mipmap-xxxhdpi\`

3. 或使用 Android Studio：
   - 右键 `app\src\main\res` > New > Image Asset

## 📝 发布到应用商店

### Debug APK → Release APK

1. **修改构建配置**
   - 编辑 `app\build.gradle.kts`
   - 修改 `signingConfigs` 部分

2. **生成签名密钥**
   ```bash
   keytool -genkey -v -keystore xinshui.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias xinshui
   ```

3. **构建 Release APK**
   ```bash
   .\gradlew.bat assembleRelease
   ```

4. **上传到商店**
   - 华为应用市场
   - 小米应用商店
   - OPPO 应用商店
   - vivo 应用商店
   - Google Play（需翻墙）

## 📱 App 功能特性

- ✅ 今日喝水记录
- ✅ 定时提醒功能
- ✅ 自定义提醒时间
- ✅ 历史记录查看
- ✅ 数据持久化
- ✅ 响应式设计
- ✅ Material Design 3

## 💡 快速测试

如果只想快速测试，可以使用 Android Studio 的模拟器：

1. **启动模拟器**
   - AVD Manager > Create Virtual Device

2. **运行应用**
   - 点击工具栏的绿色运行按钮
   - 或使用快捷键: Shift + F10
