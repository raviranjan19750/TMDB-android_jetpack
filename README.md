# TMDB Movie App

A modern Android movie app built with Jetpack Compose that displays trending movies from TMDB API with offline support.

## Features

- **Movie List**: Browse trending movies in a beautiful 2-column grid
- **Search**: Search movies with debounced input (300ms)
- **Movie Details**: View detailed information about any movie
- **Offline Support**: Movies are cached locally for offline viewing
- **Dark Mode**: Automatic dark/light theme based on system settings
- **Search History**: Recent searches are saved for quick access

## Screenshots

| Movie List (Light) | Movie Details (Light) |
|:--:|:--:|
| ![Movie List](screenshots/movie_list.png) | ![Movie Details](screenshots/movie_details.png) |

## Demo Video

[Add your demo recording here]

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

- **UI**: Jetpack Compose with Material 3
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Networking**: Retrofit + OkHttp + Moshi
- **Database**: Room
- **Image Loading**: Coil
- **Navigation**: Compose Navigation
- **Async**: Kotlin Coroutines + Flow

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/TMDB-Atlys.git
   ```

2. Get your TMDB API key:
   - Register at [TMDB](https://www.themoviedb.org/)
   - Go to Settings → API → Generate API key

3. Add your API key in `app/build.gradle.kts`:
   ```kotlin
   buildConfigField("String", "TMDB_API_KEY", "\"YOUR_API_KEY_HERE\"")
   ```

4. Build and run the app

## API Endpoints Used

- **Trending Movies**: `GET /trending/movie/week`
- **Search Movies**: `GET /search/movie`
- **Images**: `https://image.tmdb.org/t/p/{size}/{path}`

## Key Features Implementation

### Offline-First Strategy
The app implements an offline-first strategy:
1. Show cached data immediately
2. Fetch from network in background
3. Update cache and UI with fresh data
4. Show offline banner when no network

### Debounced Search
Search input is debounced by 300ms to avoid excessive API calls and provide smooth UX.

### Image Caching
Coil is configured with:
- 25% memory cache
- 5% disk cache
- Crossfade animations
- Loading shimmer placeholders

## Build Variants

- **Debug**: Logging enabled, API key for development
- **Release**: Minified, API key for production

## Requirements

- Android Studio Hedgehog or later
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 34 (Android 14)
- Kotlin 1.9.0

## License

This project is for educational purposes as part of a take-home assignment.
