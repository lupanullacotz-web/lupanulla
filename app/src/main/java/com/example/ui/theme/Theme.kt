package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LupanullaColorScheme = darkColorScheme(
    primary = NeonGreen,
    primaryContainer = CardDark,
    secondary = VividBlue,
    tertiary = GoldYellow,
    background = DeepBg,
    surface = SurfaceDark,
    onPrimary = DeepBg,
    onSecondary = Color.White,
    onTertiary = DeepBg,
    onBackground = TextMain,
    onSurface = TextMain,
    surfaceVariant = CardDark,
    onSurfaceVariant = TextMain,
    outline = BorderDark,
    error = CrimsonRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force premium dark mode by default for that eye-safe nighttime study look
    content: @Composable () -> Unit
) {
    // We enforce the customized palette to match the beautiful foundation brand aesthetic
    MaterialTheme(
        colorScheme = LupanullaColorScheme,
        typography = Typography,
        content = content
    )
}
