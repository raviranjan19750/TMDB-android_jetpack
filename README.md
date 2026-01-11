# TMDB Movie App

A modern Android movie app built with **Kotlin** and **Jetpack Compose** that displays trending movies from TMDB API with offline support. Built as a take-home assignment following Android development best practices.

## Features

### Core Requirements (PRD)
- ✅ **Movie List**: Browse trending movies in a beautiful 2-column grid (shows 20 items as per PRD)
- ✅ **Movie Details**: Click any movie to view detailed information
- ✅ **Search Functionality**: Search movies with debounced input (300ms)
- ✅ **TMDB API Integration**: Fetches trending movies from TMDB API
- ✅ **Image Loading**: Displays movie posters using TMDB image API
- ✅ **Loading States**: Shimmer placeholders while data loads
- ✅ **Empty States**: Custom empty state when no movies found
- ✅ **Error States**: Error handling with retry functionality
- ✅ **Offline Support**: Movies cached locally using Room database, works offline

### Additional Enhancements
- **Dark Mode**: Automatic dark/light theme based on system settings
- **Search History**: Recent searches saved for quick access
- **Pull-to-Refresh**: Swipe down to refresh movie list
- **Image Caching**: Coil disk cache for offline image viewing

## Demo

### Demo Videos

#### 1. Main App Demo
📹 [Watch Demo Video](app/demo/demo.mp4)

*Complete walkthrough of the app features including movie list, search, and detail screens.*

#### 2. Offline Cache Demo
📹 [Watch Offline Cache Demo](app/demo/offline_cache_demo.mp4)

*Demonstrates offline functionality - app works seamlessly without internet connection using cached data.*

#### 3. Theme Toggle Demo
📹 [Watch Theme Toggle Demo](app/demo/theme_toggle.mp4)

*Shows dark mode and light mode switching with smooth transitions.*

> **Note:** Click the links above to view the videos. For best viewing experience on GitHub, you can also download the videos or view them in the repository.

### UI Screenshots

#### Movie Listing Page
<img src="app/demo/listing_page.jpeg" width="300" alt="Movie Listing Page">

*Main screen displaying trending movies in a 2-column grid with search functionality.*

#### Movie Details Page
<img src="app/demo/details_page.jpeg" width="300" alt="Movie Details Page">

*Detailed view of a selected movie with poster, title, and overview information.*

## Architecture

The app follows **Clean Architecture** with **MVVM** pattern:

```
app/
├── data/                    # Data layer
│   ├── local/              # Room database, DAOs, entities
│   ├── remote/             # Retrofit API, DTOs
│   ├── mapper/             # Data mappers
│   └── repository/         # Repository implementations
├── di/                      # Hilt dependency injection modules
├── domain/                  # Domain layer
│   ├── model/              # Domain models
│   ├── repository/         # Repository interfaces
│   └── usecase/            # Use cases
├── presentation/            # Presentation layer
│   ├── components/         # Reusable UI components
│   ├── navigation/         # Navigation graph
│   ├── screens/            # Screen composables & ViewModels
│   └── theme/              # App theme (colors, typography)
└── util/                    # Utility classes
```

## Tech Stack

### Required by PRD
- ✅ **Language**: Kotlin
- ✅ **UI Framework**: Jetpack Compose (Material 3)
- ✅ **Navigation**: Compose Navigation
- ✅ **Third-party Libraries**:
  - **Retrofit + OkHttp**: Network calls to TMDB API
  - **Coil**: Image loading and caching
  - **Room**: Local database for offline caching
  - **Hilt**: Dependency injection
  - **Moshi**: JSON parsing

### Architecture & Patterns
- **Architecture**: MVVM + Clean Architecture
- **State Management**: Kotlin Coroutines + Flow
- **Best Practices**: Follows Android development best practices as per PRD requirements

## Setup

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17 or later
- Android SDK 24+

### Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/TMDB-Atlys.git
   cd TMDB-Atlys
   ```

2. **Get your TMDB API key** (free, takes 2 minutes):
   - Register at [TMDB](https://www.themoviedb.org/)
   - Go to **Settings** → **API** → **Create** or **Request API Key**
   - Copy your API key

3. **Add your API key** (choose one method):

   **Option A: Using the setup script (Recommended)**
   ```bash
   ./setup_api_key.sh
   ```
   Follow the prompts to enter your API key.

   **Option B: Manual setup**
   ```bash
   # Copy the example file
   cp local.properties.example local.properties
   
   # Edit local.properties and replace YOUR_API_KEY_HERE with your actual key
   # Or use the script: ./setup_api_key.sh
   ```

4. **Sync and build:**
   - Open the project in Android Studio
   - **File** → **Sync Project with Gradle Files**
   - Build and run the app (▶️ Run button)

### Security Note

🔒 **API keys are stored securely:**
- API key is stored in `local.properties` (gitignored, never committed)
- The `local.properties.example` file is a template (safe to commit)
- This follows Android security best practices
- Your API key will never be exposed in the repository

**Why this approach?**
- Protects sensitive credentials from being committed to version control
- Allows easy setup for new developers
- Follows industry-standard security practices
- Demonstrates security awareness in code

## API Integration

### TMDB API Endpoints
- **Trending Movies**: `GET /trending/movie/week?language=en-US&api_key={key}`
  - Fetches weekly trending movies (limited to 20 items as per PRD)
- **Search Movies**: `GET /search/movie?query={query}&language=en-US&api_key={key}`
  - Searches movies by query string
- **Image Base URL**: `https://image.tmdb.org/t/p/{size}/{path}`
  - Image sizes used: `w200`, `w500`, `original`

### API Documentation
- TMDB API: https://developer.themoviedb.org/reference/trending-movies
- Image Basics: https://developer.themoviedb.org/docs/image-basics

## Key Features Implementation

### State Management
- **Loading States**: Shimmer skeleton placeholders for smooth loading experience
- **Empty States**: Custom empty state design when no movies are found
- **Error States**: User-friendly error messages with retry button functionality

### Offline-First Strategy
The app implements an offline-first strategy as required by PRD:
1. Show cached data immediately from Room database
2. Fetch from network in background when online
3. Update cache and UI with fresh data
4. Display offline connectivity banner when device has no network
5. All cached data accessible offline

### Search Implementation
- **Debounced Search**: 300ms debounce to optimize API calls
- **Search History**: Recent searches saved in Room database
- **Real-time Search**: Results update as you type (with debounce)

### Image Caching
Coil is configured with:
- 25% memory cache for fast access
- 5% disk cache for offline viewing
- Crossfade animations for smooth transitions
- Loading shimmer placeholders

## Design Reference

The UI implementation follows the design provided in the PRD:
- **Figma Design**: https://www.figma.com/design/6nwpyma1zVevcAST5Bec4k/Atlys-Android-Engineer-Assignment
- **Movie List Screen**: 2-column grid layout with search bar
- **Movie Detail Screen**: Large poster, title, and overview text

## Build Variants

- **Debug**: Logging enabled, unminified code for debugging
- **Release**: Minified, obfuscated, and optimized for production

Both variants use the API key from `local.properties`.

## PRD Compliance

### ✅ Implemented Requirements
- ✅ Movie list screen with trending movies
- ✅ Movie detail screen on click
- ✅ Search functionality on list screen
- ✅ TMDB API integration
- ✅ Image loading from TMDB
- ✅ Loading, empty, and error states
- ✅ Offline access with local caching
- ✅ Built with Kotlin
- ✅ UI built with Jetpack Compose
- ✅ Navigation using Compose Navigation
- ✅ Clean, readable, and maintainable code
- ✅ Demo recording included in README

### 📋 PRD Notes (As Specified)
- ✅ **Pagination**: Not implemented (as per PRD - "not required, just show 20 items")
- ✅ **Multi-module Architecture**: Not implemented (as per PRD - "not required")
- ✅ **Unit Tests**: Not written (as per PRD - "not required")

### 📝 Commit History
This project demonstrates incremental development through commit history. All changes are committed with descriptive messages showing the development process and implementation approach.

## Troubleshooting

### "API key not found" or "Invalid API key" errors
- Ensure `local.properties` exists in the project root
- Verify `TMDB_API_KEY=your_actual_key` is set correctly
- Run `./setup_api_key.sh` to set it up again
- Sync Gradle after updating: **File** → **Sync Project with Gradle Files**

### Build errors after cloning
- Make sure you've created `local.properties` from `local.properties.example`
- The app will use a placeholder key if `local.properties` doesn't exist (will cause API errors)

## License

This project is for educational purposes as part of a take-home assignment.
