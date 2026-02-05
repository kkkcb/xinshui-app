# 心水 App - 如何获取 APK 安装包

## 📦 当前情况

您的电脑没有安装 Java 环境，无法直接构建 APK。

## 🎯 推荐方案：使用在线构建服务（无需安装任何软件）

### 方案一：使用 GitHub Actions（最简单）

#### 步骤 1：创建 GitHub 账号（如果没有）
1. 访问：https://github.com/signup
2. 注册账号（免费）

#### 步骤 2：创建仓库
1. 登录后访问：https://github.com/new
2. 仓库名：`xinshui-app`
3. 选择 "Public"（公开）
4. 点击 "Create repository"

#### 步骤 3：上传项目文件

**方法 A：通过网页上传**
1. 点击 "uploading an existing file"
2. 将 `c:\drink_water` 目录下的所有文件拖进去
3. 填写提交信息：`Initial commit`
4. 点击 "Commit changes"

**方法 B：使用 Git 命令**
```bash
cd c:\drink_water
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/你的用户名/xinshui-app.git
git push -u origin main
```

#### 步骤 4：创建构建配置

1. 在 GitHub 仓库页面点击 "Add file" > "Create new file"
2. 文件路径：`.github/workflows/build.yml`
3. 粘贴以下内容：

```yaml
name: Build Xinshui APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Grant execute permission for gradlew
      run: chmod +x gradlew

    - name: Build with Gradle
      run: ./gradlew assembleDebug

    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: xinshui-debug-apk
        path: app/build/outputs/apk/debug/app-debug.apk
        retention-days: 30
```

4. 点击 "Commit changes"

#### 步骤 5：构建 APK

1. 点击仓库顶部的 "Actions" 标签
2. 选择 "Build Xinshui APK" workflow
3. 点击右侧的 "Run workflow" 按钮
4. 点击绿色的 "Run workflow" 按钮
5. 等待 3-5 分钟

#### 步骤 6：下载 APK

1. 构建完成后，点击完成的 workflow run
2. 滚动到底部 "Artifacts" 部分
3. 点击 `xinshui-debug-apk` 下载
4. 解压后得到：`app-debug.apk`

#### 步骤 7：安装到手机

1. 将 `app-debug.apk` 传输到手机
2. 在手机上启用"未知来源应用"
3. 点击 APK 文件进行安装

---

## 🌟 方案二：使用 GitHub 在线构建工具

### 使用 Replit（更简单）

1. 访问：https://replit.com/
2. 注册账号（免费）
3. 创建新项目：Blank Repl
4. 上传所有项目文件
5. 在 Shell 中运行：
   ```bash
   chmod +x gradlew
   ./gradlew assembleDebug
   ```
6. 下载 `app/build/outputs/apk/debug/app-debug.apk`

### 使用 Gitpod（免费在线 IDE）

1. 访问：https://gitpod.io/
2. 注册账号（免费）
3. 创建工作空间
4. 上传项目文件
5. 运行构建命令

---

## 🏢 方案三：使用云端构建服务

### 使用 AppCenter（微软）

1. 访问：https://appcenter.ms/
2. 注册微软账号
3. 创建新应用
4. 上传源代码
5. 自动构建 APK

### 使用 Codemagic

1. 访问：https://codemagic.io/
2. 连接 GitHub 仓库
3. 自动构建 APK

---

## 💻 方案四：安装 Java 后本地构建

### 如果你想在本地构建，需要：

#### 1. 安装 JDK
- 下载：https://adoptium.net/
- 选择：Windows x64 > JDK 17
- 安装后重启电脑

#### 2. 设置环境变量
1. 右键"此电脑" > 属性
2. 高级系统设置 > 环境变量
3. 新建系统变量：
   - 变量名：`JAVA_HOME`
   - 变量值：`C:\Program Files\Eclipse Adoptium\jdk-17.x.x`
4. 编辑 Path，添加：`%JAVA_HOME%\bin`

#### 3. 验证安装
打开 CMD 或 PowerShell，运行：
```bash
java -version
```
如果显示版本号，说明安装成功。

#### 4. 构建 APK
```bash
cd c:\drink_water
.\gradlew.bat assembleDebug
```

#### 5. 获取 APK
位置：`app\build\outputs\apk\debug\app-debug.apk`

---

## 🎁 最终方案总结

| 方案 | 难度 | 时间 | 需要 | 推荐度 |
|------|------|------|------|--------|
| GitHub Actions | ⭐ | 5分钟 | GitHub账号 | ⭐⭐⭐⭐⭐⭐ |
| Replit | ⭐⭐ | 3分钟 | Replit账号 | ⭐⭐⭐⭐ |
| 本地构建 | ⭐⭐⭐ | 10分钟 | JDK安装 | ⭐⭐ |

## 📌 我的建议

**最推荐：GitHub Actions**
- 完全免费
- 自动化构建
- 无需本地环境
- 可以随时重新构建

**快速开始：**
1. 创建 GitHub 仓库
2. 上传文件
3. 创建 `.github/workflows/build.yml`
4. 运行 workflow
5. 下载 APK

**就这么简单！** 💕

---

## 🆘 需要帮助？

如果遇到问题，请检查：
1. GitHub 仓库是否公开
2. workflow 文件路径是否正确
3. 代码是否完整上传

详细步骤请参考：
- GitHub Actions 文档：https://docs.github.com/actions
- Gradle 构建文档：https://developer.android.com/build
