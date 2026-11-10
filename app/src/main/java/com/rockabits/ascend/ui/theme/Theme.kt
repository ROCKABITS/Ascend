package com.rockabits.ascend.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.rockabits.ascend.data.ThemeMode

val LocalIsDark = staticCompositionLocalOf { false }

private val LightScheme = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    outline = AscendColors.Border,
)

private val DarkScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color(0xFF111113),
    onSurface = Color.White,
    outline = Color(0xFF3A3A3F),
)

@Composable
fun AscendTheme(themeMode: ThemeMode, content: @Composable () -> Unit) {
    val dark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    CompositionLocalProvider(LocalIsDark provides dark) {
        MaterialTheme(
            colorScheme = if (dark) DarkScheme else LightScheme,
            typography = AscendTypography,
            content = content,
        )
    }
}
