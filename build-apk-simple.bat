@echo off
echo ========================================
echo Xinshui App - Build APK
echo ========================================
echo.

REM Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [!] Java not found
    echo [*] Downloading JDK 17...
    echo.

    if not exist "temp" mkdir temp

    powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.9%%2B9/OpenJDK17U-jdk_x64_windows_hotspot_17.0.9_9.zip' -OutFile 'temp\jdk17.zip'}"

    if %errorlevel% neq 0 (
        echo [X] JDK download failed
        pause
        exit /b 1
    )

    echo [OK] JDK downloaded
    echo [*] Extracting JDK...
    powershell -Command "Expand-Archive -Path 'temp\jdk17.zip' -DestinationPath '.' -Force"

    if %errorlevel% neq 0 (
        echo [X] JDK extraction failed
        pause
        exit /b 1
    )

    echo [OK] JDK extracted

    for /d %%i in (jdk-*) do set JDK_DIR=%%i
    set JAVA_HOME=%CD%\%JDK_DIR%
    set PATH=%JAVA_HOME%\bin;%PATH%

    echo [OK] JAVA_HOME: %JAVA_HOME%
    echo.
)

echo ========================================
echo [*] Building APK
echo ========================================
echo.

echo [*] Cleaning...
call gradlew.bat clean
if %errorlevel% neq 0 (
    echo [X] Clean failed
    pause
    exit /b 1
)

echo [*] Building Debug APK...
call gradlew.bat assembleDebug
if %errorlevel% neq 0 (
    echo [X] Build failed
    pause
    exit /b 1
)

echo.
echo ========================================
echo [OK] Build Successful!
echo ========================================
echo.

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo [*] APK Location: app\build\outputs\apk\debug\app-debug.apk
    echo.

    copy "app\build\outputs\apk\debug\app-debug.apk" "xinshui-v1.0-debug.apk" >nul
    if %errorlevel% equ 0 (
        echo [OK] Copied to: xinshui-v1.0-debug.apk
    )

    echo.
    echo ========================================
    echo [*] APK is ready!
    echo [*] File: xinshui-v1.0-debug.apk
    echo [*] Size:
    dir xinshui-v1.0-debug.apk | find "xinshui"
    echo ========================================
    echo.
    echo Press any key to exit...
    pause >nul
) else (
    echo [X] APK not found
    pause
)
