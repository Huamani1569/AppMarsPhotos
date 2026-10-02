package com.jeanpiero.marsphotos

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jeanpiero.marsphotos.ui.MarsPhotosApp
import com.jeanpiero.marsphotos.ui.theme.MarsPhotosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Íconos de la barra de estado en blanco (la barra superior es oscura)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        setContent {
            MarsPhotosTheme {
                MarsPhotosApp()
            }
        }
    }
}
