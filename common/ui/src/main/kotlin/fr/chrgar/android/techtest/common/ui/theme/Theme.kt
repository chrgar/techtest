package fr.chrgar.android.techtest.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalCardElevation = staticCompositionLocalOf { Dp.Unspecified }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val cardElevation = if (darkTheme) 0.dp else 12.dp
    CompositionLocalProvider(LocalCardElevation provides cardElevation) {
        MaterialTheme(
            typography = AppTypography,
            shapes = AppShapes,
            colorScheme = if (darkTheme) AppDarkColorScheme else AppLightColorScheme,
            content = content
        )
    }
}
