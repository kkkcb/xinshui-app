@echo off
chcp 65001 >nul
echo ========================================
echo 喝水提醒应用 - APK构建脚本
echo ========================================
echo.

echo 正在构建Debug版本的APK...
echo.

gradlew.bat assembleDebug

if %ERRORLEVEL% equ 0 (
    echo.
    echo ========================================
    echo 构建成功！
    echo ========================================
    echo.
    echo APK文件位置：
    echo app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo 现在你可以将此APK文件发送到手机上安装了。
    echo.
) else (
    echo.
    echo ========================================
    echo 构建失败！
    echo ========================================
    echo.
    echo 请检查：
    echo 1. 是否安装了JDK 17或更高版本
    echo 2. 是否安装了Android SDK
    echo 3. local.properties中的SDK路径是否正确
    echo 4. 网络连接是否正常
    echo.
)

pause
