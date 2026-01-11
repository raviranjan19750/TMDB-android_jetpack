#!/bin/bash

# Setup API Key Script
# This script helps you securely add your TMDB API key to local.properties

echo "🔑 TMDB API Key Setup"
echo ""

# Check if local.properties already exists
if [ -f "local.properties" ]; then
    echo "⚠️  local.properties already exists!"
    read -p "Do you want to overwrite it? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "❌ Setup cancelled."
        exit 0
    fi
fi

# Prompt for API key
echo "Please enter your TMDB API key:"
echo "(You can get it from: https://www.themoviedb.org/settings/api)"
echo ""
read -p "API Key: " api_key

# Validate input
if [ -z "$api_key" ]; then
    echo "❌ Error: API key cannot be empty!"
    exit 1
fi

# Create local.properties from example
if [ -f "local.properties.example" ]; then
    cp local.properties.example local.properties
else
    # Create from scratch if example doesn't exist
    echo "TMDB_API_KEY=$api_key" > local.properties
    exit 0
fi

# Replace placeholder with actual key
if [[ "$OSTYPE" == "darwin"* ]]; then
    # macOS
    sed -i '' "s/YOUR_API_KEY_HERE/$api_key/" local.properties
else
    # Linux
    sed -i "s/YOUR_API_KEY_HERE/$api_key/" local.properties
fi

echo ""
echo "✅ API key saved to local.properties"
echo "🔒 This file is gitignored and will NOT be committed to the repository."
echo ""
echo "📝 Next steps:"
echo "   1. Sync Gradle in Android Studio (File → Sync Project with Gradle Files)"
echo "   2. Build and run the app"
echo ""
