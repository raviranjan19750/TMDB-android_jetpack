# APK Build Instructions

## Generate Release APK

### Option 1: Using Android Studio (Recommended)
1. Open the project in Android Studio
2. Go to **Build** → **Generate Signed Bundle / APK**
3. Select **APK**
4. Choose your keystore (or create a new one)
5. Select **release** build variant
6. Click **Finish**
7. The APK will be generated in `app/build/outputs/apk/release/`
8. Copy it to `app/apk/` folder

### Option 2: Using Gradle Command Line
```bash
# Navigate to project root
cd /Users/raviranjan/StudioProjects/TMDBAtlys

# Build release APK
./gradlew assembleRelease

# The APK will be at:
# app/build/outputs/apk/release/app-release.apk

# Copy to apk folder
cp app/build/outputs/apk/release/app-release.apk app/apk/tmdb-atlys-release.apk
```

### Option 3: Using Gradle Wrapper (Windows)
```cmd
gradlew.bat assembleRelease
copy app\build\outputs\apk\release\app-release.apk app\apk\tmdb-atlys-release.apk
```

## Important Notes

⚠️ **Before building for production:**
1. **Replace API Key**: Update `YOUR_API_KEY_HERE` in `app/build.gradle.kts` with your actual TMDB API key
2. **Signing**: The current config uses debug keystore. For production:
   - Create a production keystore
   - Update `signingConfigs` in `build.gradle.kts`
   - Keep your keystore secure and backed up

## APK Location
- Generated APK: `app/build/outputs/apk/release/app-release.apk`
- Copy to: `app/apk/tmdb-atlys-release.apk`
