package ru.kulishov.application_showcase.presentation

import android.graphics.fonts.Font
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val lightTheme = lightColorScheme(
    surface = Color(255, 255, 255),
    onSurface = Color(33, 33, 33),
    primaryFixedDim = Color(0, 234, 255),
    surfaceContainer = Color(222, 222, 222),
    primary = Color(0, 119, 255),
    secondary = Color(255, 57, 133),
    tertiary = Color(128, 36, 192),
    error = Color(186, 26, 26)
)

val darkTheme = darkColorScheme(
    surface = Color(33, 33, 33),
    onSurface = Color(255, 255, 255),
    primaryFixedDim = Color(0, 234, 255),
    surfaceContainer = Color(222, 222, 222),
    primary = Color(0, 119, 255),
    secondary = Color(255, 57, 133),
    tertiary = Color(128, 36, 192),
    error = Color(186, 26, 26)

)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val currentColorScheme = if (isSystemInDarkTheme()) darkTheme else lightTheme
    MaterialTheme(
        content = content,
        colorScheme = currentColorScheme,
        typography = Typography(
            headlineLarge = TextStyle(
                fontSize = 26.sp,
                color = currentColorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                letterSpacing = (26.sp * 0.02f)
            ),
            titleLarge = TextStyle(
                fontSize = 22.sp,
                color = currentColorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (26.sp * 0.02f)
            ),
            titleMedium = TextStyle(
                fontSize = 18.sp,
                color = currentColorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (26.sp * 0.02f)
            ),
            titleSmall = TextStyle(
                fontSize = 16.sp,
                color = currentColorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (26.sp * 0.02f)
            ),
            bodyMedium = TextStyle(
                fontSize = 16.sp,
                color = currentColorScheme.onSurface,
                fontWeight = FontWeight.Normal,
                letterSpacing = (26.sp * 0.02f)
            ),
            bodySmall = TextStyle(
                fontSize = 11.sp,
                color = currentColorScheme.onSurface,
                fontWeight = FontWeight.Normal,
            ),
        )
    )
}