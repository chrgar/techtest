package fr.chrgar.android.techtest.common.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private object AppColors {
    val DarkBlue = Color(0xFF141B4D)
    val Blue = Color(0xFF50AEFF)
    val Grey = Color(0xFF8C8C8C)
    val DarkGrey = Color(0xFF1C1C1C)
    val Black = Color(0xFF000000)
    val White = Color(0xFFFFFFFF)
}

val AppLightColorScheme = lightColorScheme(
    primary = AppColors.DarkBlue,
    surface = AppColors.DarkBlue,
    onSurface = AppColors.White,
    secondary = AppColors.White,
    secondaryContainer = AppColors.White,
    tertiary = AppColors.Black,
    outline = AppColors.Grey,
    outlineVariant = AppColors.Blue
)

val AppDarkColorScheme = darkColorScheme(
    primary = AppColors.DarkBlue,
    surface = AppColors.DarkBlue,
    onSurface = AppColors.White,
    secondary = AppColors.Black,
    secondaryContainer = AppColors.DarkGrey,
    tertiary = AppColors.White,
    outline = AppColors.Grey,
    outlineVariant = AppColors.Blue
)