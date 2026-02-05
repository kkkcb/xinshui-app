@echo off
chcp 65001 >nul
title 心水 App - 一键打包
color 0B

echo.
echo ========================================
echo        心水 App - 一键打包工具
echo ========================================
echo.

REM 检查是否已安装 Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ 未检测到 Java 环境
    echo.
    echo 正在自动下载 JDK...
    echo.

    REM 创建临时目录
    if not exist "temp" mkdir temp

    REM 下载 JDK (Adoptium)
    echo 📥 正在下载 JDK 17...
    powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.9%%2B9/OpenJDK17U-jdk_x64_windows_hotspot_17.0.9_9.zip' -OutFile 'temp\jdk17.zip'}"

    if %errorlevel% neq 0 (
        echo ❌ JDK 下载失败
        echo.
        echo 请手动下载 JDK:
        echo https://adoptium.net/
        pause
        exit /b 1
    )

    echo ✅ JDK 下载完成

    REM 解压 JDK
    echo 📦 正在解压 JDK...
    powershell -Command "Expand-Archive -Path 'temp\jdk17.zip' -DestinationPath '.' -Force"

    if %errorlevel% neq 0 (
        echo ❌ JDK 解压失败
        pause
        exit /b 1
    )

    echo ✅ JDK 解压完成

    REM 设置 JAVA_HOME
    for /d %%i in (jdk-*) do set JDK_DIR=%%i
    set JAVA_HOME=%CD%\%JDK_DIR%
    set PATH=%JAVA_HOME%\bin;%PATH%

    echo 🎯 JAVA_HOME 已设置: %JAVA_HOME%
    echo.
)

echo ========================================
echo 📱 开始构建心水 App
echo ========================================
echo.

REM 清理之前的构建
echo 🧹 清理缓存...
call gradlew.bat clean
if %errorlevel% neq 0 (
    echo ❌ 清理失败
    pause
    exit /b 1
)

echo ✅ 清理完成
echo.

REM 构建 Debug APK
echo 🔨 正在构建 APK...
echo 这可能需要几分钟时间...
echo.

call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
    echo ❌ 构建失败
    echo.
    echo 可能的原因:
    echo 1. 网络连接问题
    echo 2. Android SDK 未安装
    echo 3. Gradle 依赖下载失败
    echo.
    echo 请检查错误信息并重试
    pause
    exit /b 1
)

echo.
echo ========================================
echo ✅ 构建成功！
echo ========================================
echo.

REM 检查 APK 是否生成
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo 📦 APK 文件位置:
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.

    REM 复制到根目录方便查找
    copy "app\build\outputs\apk\debug\app-debug.apk" "心水-v1.0-debug.apk" >nul
    if %errorlevel% equ 0 (
        echo ✅ 已复制到根目录: 心水-v1.0-debug.apk
    )

    echo.
    echo ========================================
    echo 📲 安装说明
    echo ========================================
    echo.
    echo 1. 将心水-v1.0-debug.apk传输到手机
    echo 2. 在手机上启用"未知来源应用"
    echo 3. 点击 APK 文件进行安装
    echo.

    REM 询问是否打开文件夹
    echo 是否打开 APK 所在文件夹? (Y/N)
    set /p choice=
    if /i "%choice%"=="Y" (
        explorer "app\build\outputs\apk\debug\"
    )

    REM 询问是否立即安装
    echo.
    echo 是否立即安装到连接的设备? (Y/N)
    set /p install_choice=
    if /i "%install_choice%"=="Y" (
        echo.
        echo 正在安装到设备...
        adb install -r "app\build\outputs\apk\debug\app-debug.apk"
    )
) else (
    echo ❌ 未找到 APK 文件
    echo.
    echo 请检查构建日志
)

echo.
echo 按任意键退出...
pause >nul
