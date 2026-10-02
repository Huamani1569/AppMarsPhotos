@file:OptIn(ExperimentalMaterial3Api::class)

package com.jeanpiero.marsphotos.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeanpiero.marsphotos.ui.screens.FotoDetalleScreen
import com.jeanpiero.marsphotos.ui.screens.HomeScreen
import com.jeanpiero.marsphotos.viewmodel.MarsViewModel

// Composable raíz: obtiene el ViewModel (con su Factory) y decide qué pantalla mostrar.
@Composable
fun MarsPhotosApp() {
    val marsViewModel: MarsViewModel = viewModel(factory = MarsViewModel.Factory)
    // Se guarda aquí para que la cuadrícula conserve su posición al volver del detalle
    val gridState = rememberLazyGridState()

    val foto = marsViewModel.fotoSeleccionada
    if (foto != null) {
        BackHandler { marsViewModel.cerrarFoto() }
        FotoDetalleScreen(photo = foto, onVolver = { marsViewModel.cerrarFoto() })
    } else {
        PantallaPrincipal(marsViewModel, gridState)
    }
}

@Composable
private fun PantallaPrincipal(marsViewModel: MarsViewModel, gridState: LazyGridState) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Mars Photos", fontWeight = FontWeight.Bold)
                        Text("Semana 7 · MVVM + Retrofit", style = MaterialTheme.typography.bodySmall)
                    }
                },
                actions = {
                    IconButton(onClick = { marsViewModel.getMarsPhotos() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        HomeScreen(
            marsUiState = marsViewModel.marsUiState,
            retryAction = { marsViewModel.getMarsPhotos() },
            onFotoClick = { marsViewModel.verFoto(it) },
            gridState = gridState,
            contentPadding = padding
        )
    }
}
