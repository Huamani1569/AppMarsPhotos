package com.jeanpiero.marsphotos

import android.app.Application
import com.jeanpiero.marsphotos.data.AppContainer
import com.jeanpiero.marsphotos.data.DefaultAppContainer

// Clase Application (registrada en el AndroidManifest con android:name).
// Se crea antes que cualquier pantalla y guarda el contenedor de dependencias.
class MarsPhotosApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
