# Mars Photos 🪐

Práctica 01 – *Arquitectura REST*: codelab oficial de Android "Obtener datos de Internet" (Mars Photos) con **MVVM**, repositorio e inyección manual de dependencias.

**Estudiante:** Jean Piero Huamani Rodriguez · **Curso:** Desarrollo de Aplicaciones Móviles · **Semana 7:** MVVM + servicios web REST con Retrofit

Kotlin · Jetpack Compose · MVVM · Retrofit 2 · Gson · Coil

## Servicio web consumido
`GET https://android-kotlin-fun-mars-server.appspot.com/photos`

## Funcionalidades
- Descarga las fotos del rover Curiosity (JSON con `id` e `img_src`) y muestra cuántas se recibieron.
- Cuadrícula de fotos (`LazyVerticalGrid`) cargadas con Coil, con imagen de carga y de error.
- Detalle de la foto en grande con los datos recibidos del API.
- Estados Loading / Success / Error con botón **Reintentar** y botón de actualizar.

## Arquitectura MVVM
Sigue la arquitectura recomendada del codelab: la **View** no conoce a Retrofit; el **ViewModel** depende de la interfaz `MarsPhotosRepository`, y el `AppContainer` (creado en la clase `Application`) le entrega la implementación que usa la red. Diferencia con el codelab: se usa el conversor **Gson** (igual que en la sesión de clase) en lugar de kotlinx.serialization.

```
View (Compose)  ──eventos──▶  ViewModel  ──pide datos──▶  Repositorio  ──▶  Retrofit (ApiService)  ──HTTP GET──▶  API REST
      ▲                          │                                                                         │
      └────── estado (UiState) ──┘◀──────────────────────── objetos Kotlin (Gson) ◀──────── JSON ────────────┘
```

```
com.jeanpiero.marsphotos/
├── model/MarsPhoto.kt                // MODELO (@SerializedName("img_src"))
├── network/MarsApiService.kt         // Interface del API (@GET("photos"))
├── data/
│   ├── MarsPhotosRepository.kt       // interfaz + NetworkMarsPhotosRepository
│   └── AppContainer.kt               // crea Retrofit y el repositorio (DI manual)
├── viewmodel/MarsViewModel.kt        // MarsUiState + Factory
├── ui/
│   ├── MarsPhotosApp.kt              // Scaffold + TopAppBar + navegación
│   ├── screens/HomeScreen.kt, FotoDetalleScreen.kt
│   └── theme/
├── MarsPhotosApplication.kt          // guarda el AppContainer
└── MainActivity.kt
```

## Abrir y generar el APK
1. Android Studio → **File → Open** → carpeta `AppMarsPhotos` → espera el **Gradle Sync**.
2. Ejecuta con ▶ **Run** (el celular o emulador necesita internet).
3. APK: **Build → Generate App Bundles or APKs → Generate APKs** → queda en `app/build/outputs/apk/debug/app-debug.apk`.
