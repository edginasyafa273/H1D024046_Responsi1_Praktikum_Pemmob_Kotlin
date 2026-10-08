package com.pemmob.resepku.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDeepRed,
    onPrimary = RedOnPrimaryLight,
    primaryContainer = RedPrimaryContainerLight,
    onPrimaryContainer = RedOnPrimaryContainerLight,
    secondary = SecondaryDeepRed,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainerWarm,
    onSecondaryContainer = OnSecondaryContainerWarm,
    tertiary = TertiaryWarm,
    background = RedBackgroundLight,
    onBackground = RedOnSurfaceLight,
    surface = RedSurfaceLight,
    onSurface = RedOnSurfaceLight,
    surfaceVariant = RedSurfaceVariantLight,
    onSurfaceVariant = RedOnSurfaceVariantLight,
    outline = RedOutlineLight,
    outlineVariant = Color(0xFFEAEAEA),
    error = Color(0xFFBA1A1A),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimaryDarkTheme,
    onPrimary = RedOnPrimaryDark,
    primaryContainer = RedPrimaryContainerDark,
    onPrimaryContainer = RedOnPrimaryContainerDark,
    secondary = SecondaryDeepRed,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4A1215),
    onSecondaryContainer = Color(0xFFFFD9DA),
    background = RedBackgroundDark,
    onBackground = RedOnSurfaceDark,
    surface = RedSurfaceDark,
    onSurface = RedOnSurfaceDark,
    surfaceVariant = RedSurfaceVariantDark,
    onSurfaceVariant = RedOnSurfaceVariantDark,
    outline = RedOutlineDark,
    outlineVariant = Color(0xFF382324),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun ResepKuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set dynamicColor ke false agar warna merah tua pekat tetap konsisten di semua perangkat
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}