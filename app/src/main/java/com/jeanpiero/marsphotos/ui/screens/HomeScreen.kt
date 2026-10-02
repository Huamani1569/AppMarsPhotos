package com.jeanpiero.marsphotos.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jeanpiero.marsphotos.model.MarsPhoto
import com.jeanpiero.marsphotos.viewmodel.MarsUiState

// VISTA principal: según el estado del ViewModel muestra cargando, error o la cuadrícula de fotos.
@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    retryAction: () -> Unit,
    onFotoClick: (MarsPhoto) -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Success -> PhotosGridScreen(
            photos = marsUiState.photos,
            onFotoClick = onFotoClick,
            gridState = gridState,
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )
        is MarsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text("Descargando fotos de Marte...", modifier = Modifier.padding(top = 12.dp))
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.padding(24.dp), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                Icons.Default.CloudOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Text("No se pudieron cargar las fotos. Revisa tu conexión a internet.", textAlign = TextAlign.Center)
            Button(onClick = retryAction) { Text("Reintentar") }
        }
    }
}

// Cuadrícula (LazyVerticalGrid) con todas las fotos recibidas del API
@Composable
fun PhotosGridScreen(
    photos: List<MarsPhoto>,
    onFotoClick: (MarsPhoto) -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        state = gridState,
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        // Encabezado que ocupa todo el ancho de la cuadrícula
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                "Éxito: ${photos.size} fotos de Marte recibidas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(8.dp)
            )
        }
        items(items = photos) { photo ->
            MarsPhotoCard(
                photo = photo,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clickable { onFotoClick(photo) }
            )
        }
    }
}

// Tarjeta con una foto: Coil la descarga de internet usando la URL del JSON
@Composable
fun MarsPhotoCard(photo: MarsPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc.replace("http://", "https://"))
                .crossfade(true)
                .build(),
            error = rememberVectorPainter(Icons.Default.BrokenImage),
            placeholder = rememberVectorPainter(Icons.Default.Image),
            contentDescription = "Foto de Marte ${photo.id}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
