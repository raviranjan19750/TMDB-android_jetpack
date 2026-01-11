#!/bin/bash

# Build Release APK Script
# This script builds the release APK and copies it to app/apk folder

echo "🔨 Building Release APK..."

# Navigate to project root
cd "$(dirname "$0")"

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build release APK
echo "📦 Building release APK..."
./gradlew assembleRelease

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    
    # Create apk folder if it doesn't exist
    mkdir -p app/apk
    
    # Copy APK to apk folder with timestamp
    TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
    APK_NAME="tmdb-atlys-release-${TIMESTAMP}.apk"
    
    cp app/build/outputs/apk/release/app-release.apk "app/apk/${APK_NAME}"
    
    echo "📱 APK copied to: app/apk/${APK_NAME}"
    echo "📱 Also available at: app/build/outputs/apk/release/app-release.apk"
else
    echo "❌ Build failed! Please check the errors above."
    exit 1
fi
