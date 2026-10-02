package com.jeanpiero.marsphotos.data

import com.jeanpiero.marsphotos.model.MarsPhoto
import com.jeanpiero.marsphotos.network.MarsApiService

// Repositorio: el ViewModel depende de esta interfaz y no de Retrofit directamente.
// Así se podría cambiar la fuente de datos (o usar datos falsos en pruebas) sin tocar el ViewModel.
interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

// Implementación que obtiene las fotos desde el servicio web
class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}
