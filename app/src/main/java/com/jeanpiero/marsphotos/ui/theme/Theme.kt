package com.jeanpiero.marsphotos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Colores inspirados en la superficie de Marte
val RojoMarte = Color(0xFFB5482A)
val ArenaMarte = Color(0xFFFFDBCF)
val NaranjaPolvo = Color(0xFFF4A261)
val FondoArena = Color(0xFFFFF8F5)
val TextoOscuro = Color(0xFF231917)
val TextoGris = Color(0xFF53433F)

private val EsquemaMarte = lightColorScheme(
    primary = RojoMarte,
    onPrimary = Color.White,
    primaryContainer = ArenaMarte,
    onPrimaryContainer = TextoOscuro,
    secondary = NaranjaPolvo,
    onSecondary = TextoOscuro,
    background = FondoArena,
    onBackground = TextoOscuro,
    surface = Color.White,
    onSurface = TextoOscuro,
    onSurfaceVariant = TextoGris
)

@Composable
fun MarsPhotosTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EsquemaMarte, content = content)
}
