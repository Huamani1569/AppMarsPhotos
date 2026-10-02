package com.jeanpiero.marsphotos.data

import com.jeanpiero.marsphotos.network.MarsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Contenedor de dependencias (inyección manual, como en el codelab de Android):
// crea Retrofit y el repositorio una sola vez para toda la aplicación.
interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com/"

    // Retrofit convierte el JSON a objetos Kotlin con Gson
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

    override val marsPhotosRepository: MarsPhotosRepository by lazy {
        NetworkMarsPhotosRepository(retrofitService)
    }
}
