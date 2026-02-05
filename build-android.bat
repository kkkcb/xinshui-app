@echo off
chcp 65001 >nul
echo ========================================
echo        心水 App 安卓打包脚本
echo ========================================
echo.

if not exist "package.json" (
    echo ❌ 错误: 未找到 package.json 文件
    echo 请先运行: npm install
    pause
    exit /b 1
)

echo 📦 第一步: 安装依赖...
call npm install
if errorlevel 1 (
    echo ❌ 依赖安装失败
    pause
    exit /b 1
)
echo ✅ 依赖安装完成
echo.

echo 🔧 第二步: 初始化 Capacitor...
if not exist "capacitor.config.json" (
    echo ⚠️  配置文件已存在，跳过初始化
) else (
    call npx cap init "心水" com.drinkwater.app
    echo ✅ Capacitor 初始化完成
)
echo.

echo 📱 第三步: 添加 Android 平台...
call npx cap add android
if errorlevel 1 (
    echo ⚠️  Android 平台可能已存在
) else (
    echo ✅ Android 平台添加完成
)
echo.

echo 🔄 第四步: 同步文件到 Android 项目...
call npx cap sync
if errorlevel 1 (
    echo ❌ 同步失败
    pause
    exit /b 1
)
echo ✅ 文件同步完成
echo.

echo ✨ 打包准备完成！
echo.
echo 下一步操作:
echo 1. 运行: npx cap open android
echo 2. 在 Android Studio 中打开项目
echo 3. 点击 Build > Build Bundle(s) / APK(s) > Build APK(s)
echo 4. APK 文件将生成在: android\app\build\outputs\apk\debug\
echo.
pause
