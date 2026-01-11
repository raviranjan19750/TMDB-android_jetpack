#!/bin/bash

# Build Optimized Release APK Script
# This script builds an optimized release APK for sharing with colleagues
# The APK is minified, obfuscated, and has unused resources removed

echo "🔨 Building Optimized Release APK..."

# Navigate to project root
cd "$(dirname "$0")"

# Check for Java
check_java() {
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | head -n 1)
        echo "✅ Found Java: $JAVA_VERSION"
        return 0
    fi
    
    # Check for Android Studio's bundled JDK (macOS)
    if [ -d "$HOME/Library/Android/sdk/jbr/Contents/Home" ]; then
        export JAVA_HOME="$HOME/Library/Android/sdk/jbr/Contents/Home"
        export PATH="$JAVA_HOME/bin:$PATH"
        echo "✅ Using Android Studio's bundled JDK"
        return 0
    fi
    
    # Check for Android Studio's bundled JDK (alternative location)
    if [ -d "/Applications/Android Studio.app/Contents/jbr/Contents/Home" ]; then
        export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
        export PATH="$JAVA_HOME/bin:$PATH"
        echo "✅ Using Android Studio's bundled JDK"
        return 0
    fi
    
    echo "❌ Java not found!"
    echo ""
    echo "Please install Java or set JAVA_HOME:"
    echo "1. Install JDK 17 from: https://adoptium.net/"
    echo "2. Or set JAVA_HOME to Android Studio's JDK:"
    echo "   export JAVA_HOME=\"/Applications/Android Studio.app/Contents/jbr/Contents/Home\""
    echo "   export PATH=\"\$JAVA_HOME/bin:\$PATH\""
    echo ""
    echo "Then run this script again."
    return 1
}

# Check Java before proceeding
if ! check_java; then
    exit 1
fi

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
