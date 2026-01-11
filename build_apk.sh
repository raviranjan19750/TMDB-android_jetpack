#!/bin/bash

# Build Optimized Release APK Script
# This script builds an optimized release APK for sharing with colleagues
# The APK is minified, obfuscated, and has unused resources removed

echo "🔨 Building Optimized Release APK..."

# Navigate to project root
cd "$(dirname "$0")"

# Clean previous builds
echo "🧹 Cleaning previous builds..."
./gradlew clean

# Build optimized release APK
echo "📦 Building optimized release APK (this may take a few minutes)..."
./gradlew assembleRelease

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    
    # Create apk folder if it doesn't exist
    mkdir -p app/apk
    
    # Copy APK to apk folder with descriptive name
    TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
    APK_NAME="tmdb-atlys-optimized-${TIMESTAMP}.apk"
    
    cp app/build/outputs/apk/release/app-release.apk "app/apk/${APK_NAME}"
    
    # Get APK size
    APK_SIZE=$(du -h "app/apk/${APK_NAME}" | cut -f1)
    
    echo ""
    echo "✅ Optimized APK generated successfully!"
    echo "📱 Location: app/apk/${APK_NAME}"
    echo "📦 Size: ${APK_SIZE}"
    echo ""
    echo "📋 APK Features:"
    echo "   ✓ Minified and obfuscated code"
    echo "   ✓ Unused resources removed"
    echo "   ✓ Optimized for smaller size"
    echo "   ✓ Ready to share with colleagues"
    echo ""
    echo "💡 To install: Enable 'Install from Unknown Sources' on Android device"
    echo "   and transfer this APK file."
else
    echo "❌ Build failed! Please check the errors above."
    exit 1
fi
