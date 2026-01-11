# Optimized APK Build Instructions

This folder contains optimized release APKs for sharing with colleagues.

## Quick Build (Recommended)

### Using the Build Script
```bash
cd /Users/raviranjan/StudioProjects/TMDBAtlys
./build_apk.sh
```

The script will:
- Clean previous builds
- Build an optimized release APK
- Copy it to `app/apk/` with timestamp
- Show APK size and location

## Manual Build

### Option 1: Using Android Studio
1. Open the project in Android Studio
2. Go to **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
3. Select **release** build variant
4. Click **Build**
5. The APK will be at: `app/build/outputs/apk/release/app-release.apk`
6. Copy it to `app/apk/` folder

### Option 2: Using Gradle Command Line
```bash
# Navigate to project root
cd /Users/raviranjan/StudioProjects/TMDBAtlys

# Build optimized release APK
./gradlew assembleRelease

# Copy to apk folder
cp app/build/outputs/apk/release/app-release.apk app/apk/tmdb-atlys-optimized.apk
```

## APK Optimization Features

The release APK includes:
- ✅ **Code minification** - Reduces code size
- ✅ **Code obfuscation** - Makes reverse engineering harder
- ✅ **Resource shrinking** - Removes unused resources
- ✅ **Optimized for smaller size** - Better for sharing

## Important Notes

⚠️ **Before building:**
1. **Replace API Key**: Update `YOUR_API_KEY_HERE` in `app/build.gradle.kts` with your actual TMDB API key (in 3 places: defaultConfig, debug, and release)

## Sharing the APK

1. **Transfer the APK** to your colleagues (via email, file sharing, etc.)
2. **Installation**: Recipients need to:
   - Enable "Install from Unknown Sources" on their Android device
   - Open the APK file
   - Follow the installation prompts

## APK Location
- Generated APK: `app/build/outputs/apk/release/app-release.apk`
- Copied to: `app/apk/tmdb-atlys-optimized-[timestamp].apk`
