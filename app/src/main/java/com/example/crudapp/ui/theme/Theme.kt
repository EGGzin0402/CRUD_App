package com.example.crudapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val black1 = Color(0xFF000000)
private val black2 = Color(0xFF151515)
private val black3 = Color(0xFF303030)
private val white1 = Color(0xFFFFFFFF)
private val white2 = Color(0xFFDADADA)
private val white3 = Color(0xFFC5C5C5)

private val DarkColorScheme = darkColorScheme(
    primary = white1,
    onPrimary = black1,
    primaryContainer = black1,
    onPrimaryContainer = white1,
    inversePrimary = white1,
    secondary = white2,
    onSecondary = black1,
    secondaryContainer = black2,
    onSecondaryContainer = black3,
    tertiary = black3,
    onTertiary = white1,
    tertiaryContainer = black3,
    onTertiaryContainer = white1,
    background = black1,
    onBackground = white1,
    surface = black1,
    onSurface = white1,
    surfaceVariant = black3,
    onSurfaceVariant = white1,
    surfaceTint = black1,
    inverseSurface = white1,
    inverseOnSurface = white2,
    onError = white1,
    errorContainer = black3,
    onErrorContainer = white1,
    outline = white1,
    outlineVariant = white2,
    scrim = white1,
    surfaceBright = white2,
    surfaceContainer = white2,
    surfaceContainerHigh = white3,
    surfaceContainerHighest = white3,
    surfaceContainerLow = white3,
    surfaceContainerLowest = white1,
    surfaceDim = white2,

)

private val LightColorScheme = lightColorScheme(
    primary = black1,
    onPrimary = white1,
    primaryContainer = white1,
    onPrimaryContainer = black1,
    inversePrimary = black1,
    secondary = black2,
    onSecondary = white1,
    secondaryContainer = white2,
    onSecondaryContainer = white3,
    tertiary = white3,
    onTertiary = black1,
    tertiaryContainer = white3,
    onTertiaryContainer = black1,
    background = white1,
    onBackground = black1,
    surface = white1,
    onSurface = black1,
    surfaceVariant = white3,
    onSurfaceVariant = black1,
    surfaceTint = white1,
    inverseSurface = black1,
    inverseOnSurface = black2,
    onError = black1,
    errorContainer = white3,
    onErrorContainer = black1,
    outline = black1,
    outlineVariant = black2,
    scrim = black1,
    surfaceBright = black2,
    surfaceContainer = black2,
    surfaceContainerHigh = black3,
    surfaceContainerHighest = black3,
    surfaceContainerLow = black3,
    surfaceContainerLowest = black1,
    surfaceDim = black2,
)

@Composable
fun CRUDAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}