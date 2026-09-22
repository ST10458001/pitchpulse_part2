package com.pitchpulse.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val CyanPulse = Color(0xFF2E86AB)
val Charcoal = Color(0xFF1B1B1E)
val CharcoalLight = Color(0xFF26262B)

private val PitchPulseColorScheme = darkColorScheme(
    primary = CyanPulse,
    onPrimary = Color.White,
    background = Charcoal,
    onBackground = Color.White,
    surface = CharcoalLight,
    onSurface = Color.White,
    error = Color(0xFFCF6679)
)

@Composable
fun PitchPulseTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PitchPulseColorScheme,
        content = content
    )
}
