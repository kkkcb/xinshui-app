---
name: "github-apk-builder"
description: "Automates GitHub Actions setup for Capacitor APK builds. Invoke when user wants to deploy Web app as Android APK or needs CI/CD setup."
---

# GitHub APK Builder

This skill automates the complete setup of GitHub Actions workflow for building Android APKs from Capacitor-based Web applications. It handles the entire CI/CD pipeline configuration.

## When to Use

- User wants to build Android APK from Web app
- User needs GitHub Actions CI/CD setup
- User wants to deploy Capacitor app automatically
- User mentions "build APK", "CI/CD", or "automatic deployment"

## Prerequisites

- Capacitor project with `capacitor.config.json`
- Node.js project with `package.json`
- Web content directory (default: `www/`)
- GitHub repository

## What This Skill Does

1. **Validates project structure**
   - Checks for `capacitor.config.json`
   - Verifies `package.json` has Capacitor dependencies
   - Ensures web directory exists

2. **Creates GitHub Actions workflow**
   - Generates `.github/workflows/build.yml`
   - Configures Node.js setup
   - Sets up Capacitor build steps
   - Adds APK upload artifact

3. **Triggers build**
   - Pushes workflow to GitHub
   - Optionally triggers manual workflow run

## Usage

Invoke this skill by saying any of:
- "Set up GitHub Actions for APK builds"
- "Create CI/CD for my app"
- "Deploy my app automatically"
- "Help me build APKs on GitHub"

## Configuration

The skill will prompt for:
- **App name**: Displayed in APK (default: from capacitor.config.json)
- **Package name**: com.company.app (default: from capacitor.config.json)
- **Web directory**: Where HTML files are located (default: www)
- **Node version**: For GitHub Actions (default: 18)
- **APK name**: Artifact name (default: app-debug-apk)

## Generated Workflow

The skill creates a workflow that:

1. Checks out code
2. Sets up Node.js 18
3. Installs dependencies (`npm install`)
4. Adds Android platform (`npx cap add android`)
5. Syncs Capacitor (`npx cap sync android`)
6. Sets up JDK 17
7. Builds APK (`./gradlew assembleDebug`)
8. Uploads APK as artifact

## After Setup

- APK automatically builds on push to main branch
- Manual trigger available via GitHub UI
- APK available in Actions > Artifacts
- Retention: 30 days

## Troubleshooting

If build fails, check:
1. `capacitor.config.json` has correct `webDir`
2. `package.json` has `@capacitor/android` dependency
3. Web directory contains `index.html`
4. Android SDK version compatibility (minSdk 24+)
