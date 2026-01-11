package com.example.tmdb_atlys.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tmdb_atlys.presentation.theme.DarkOfflineBannerBackground
import com.example.tmdb_atlys.presentation.theme.DarkOfflineBannerText
import com.example.tmdb_atlys.presentation.theme.OfflineBannerBackground
import com.example.tmdb_atlys.presentation.theme.OfflineBannerText

/**
 * Banner displayed when the device is offline.
 * Shows a warning message indicating cached data is being displayed.
 */
@Composable
fun ConnectivityBanner(
    isOffline: Boolean,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) DarkOfflineBannerBackground else OfflineBannerBackground
    val textColor = if (isDarkTheme) DarkOfflineBannerText else OfflineBannerText
    
    AnimatedVisibility(
        visible = isOffline,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WifiOff,
                    contentDescription = "Offline",
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
                
                Text(
                    text = "You're offline. Showing cached data.",
                    color = textColor,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
