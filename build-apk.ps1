# Xinshui App - Build APK Script
# Usage: Right-click and select "Run with PowerShell" or run: .\build-apk.ps1

$ErrorActionPreference = "Stop"

Write-Host ""
Write-Host "========================================"  -ForegroundColor Cyan
Write-Host "  Xinshui App - Build APK"  -ForegroundColor Cyan
Write-Host "========================================"  -ForegroundColor Cyan
Write-Host ""

# Check Java
Write-Host "[*] Checking Java environment..." -ForegroundColor Yellow
$javaVersion = & java -version 2>&1 | Select-String "version"
if ($LASTEXITCODE -ne 0) {
    Write-Host "[!] Java not found" -ForegroundColor Red
    Write-Host "[*] Downloading JDK 17..." -ForegroundColor Yellow
    Write-Host ""

    # Create temp directory
    if (-not (Test-Path "temp")) {
        New-Item -ItemType Directory -Path "temp" | Out-Null
    }

    # Download JDK
    Write-Host "[*] Downloading JDK 17 from Adoptium..." -ForegroundColor Yellow
    $jdkUrl = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.9%2B9/OpenJDK17U-jdk_x64_windows_hotspot_17.0.9_9.zip"
    $jdkZip = "temp\jdk17.zip"

    try {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        Invoke-WebRequest -Uri $jdkUrl -OutFile $jdkZip -UseBasicParsing
    } catch {
        Write-Host "[X] JDK download failed: $_" -ForegroundColor Red
        Write-Host "[*] Manual download: https://adoptium.net/" -ForegroundColor Yellow
        pause
        exit 1
    }

    Write-Host "[OK] JDK downloaded" -ForegroundColor Green
    Write-Host "[*] Extracting JDK..." -ForegroundColor Yellow

    # Extract JDK
    try {
        Expand-Archive -Path $jdkZip -DestinationPath "." -Force
    } catch {
        Write-Host "[X] JDK extraction failed: $_" -ForegroundColor Red
        pause
        exit 1
    }

    Write-Host "[OK] JDK extracted" -ForegroundColor Green

    # Find JDK directory
    $jdkDir = Get-ChildItem -Filter "jdk-*" -Directory | Select-Object -First 1
    $env:JAVA_HOME = $jdkDir.FullName
    $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

    Write-Host "[OK] JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Green
    Write-Host ""
}

# Build APK
Write-Host "========================================"  -ForegroundColor Cyan
Write-Host "[*] Building APK"  -ForegroundColor Cyan
Write-Host "========================================"  -ForegroundColor Cyan
Write-Host ""

# Clean
Write-Host "[*] Cleaning..." -ForegroundColor Yellow
& ".\gradlew.bat" clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "[X] Clean failed" -ForegroundColor Red
    pause
    exit 1
}

# Build Debug APK
Write-Host "[*] Building Debug APK..." -ForegroundColor Yellow
Write-Host "[!] This may take several minutes..." -ForegroundColor Yellow
Write-Host ""

& ".\gradlew.bat" assembleDebug
if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "[X] Build failed" -ForegroundColor Red
    Write-Host ""
    Write-Host "Possible reasons:" -ForegroundColor Yellow
    Write-Host "  1. Network connection issues" -ForegroundColor Yellow
    Write-Host "  2. Android SDK not installed" -ForegroundColor Yellow
    Write-Host "  3. Gradle dependency download failed" -ForegroundColor Yellow
    Write-Host ""
    pause
    exit 1
}

Write-Host ""
Write-Host "========================================"  -ForegroundColor Cyan
Write-Host "[OK] Build Successful!"  -ForegroundColor Green
Write-Host "========================================"  -ForegroundColor Cyan
Write-Host ""

# Check APK
$apkPath = "app\build\outputs\apk\debug\app-debug.apk"
if (Test-Path $apkPath) {
    Write-Host "[*] APK Location: $apkPath" -ForegroundColor Green
    Write-Host ""

    # Copy to root
    $destApk = "xinshui-v1.0-debug.apk"
    Copy-Item -Path $apkPath -Destination $destApk -Force

    Write-Host "[OK] Copied to: $destApk" -ForegroundColor Green
    Write-Host ""

    # Get file size
    $apkSize = (Get-Item $destApk).Length / 1MB
    Write-Host "========================================"  -ForegroundColor Cyan
    Write-Host "[*] APK is ready!"  -ForegroundColor Green
    Write-Host "[*] File: $destApk"  -ForegroundColor Green
    Write-Host "[*] Size: $([math]::Round($apkSize, 2)) MB"  -ForegroundColor Green
    Write-Host "========================================"  -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Installation:" -ForegroundColor Yellow
    Write-Host "  1. Transfer xinshui-v1.0-debug.apk to phone" -ForegroundColor Yellow
    Write-Host "  2. Enable 'Unknown Sources' in phone settings" -ForegroundColor Yellow
    Write-Host "  3. Click APK to install" -ForegroundColor Yellow
    Write-Host ""

    # Open folder
    Write-Host "Opening APK folder..." -ForegroundColor Yellow
    Start-Process "explorer.exe" -ArgumentList (Split-Path $apkPath)
} else {
    Write-Host "[X] APK not found" -ForegroundColor Red
    Write-Host "[*] Check build logs above" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Yellow
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
