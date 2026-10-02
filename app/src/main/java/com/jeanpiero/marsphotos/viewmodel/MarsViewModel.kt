package com.jeanpiero.marsphotos.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jeanpiero.marsphotos.MarsPhotosApplication
import com.jeanpiero.marsphotos.data.MarsPhotosRepository
import com.jeanpiero.marsphotos.model.MarsPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Estados de la pantalla principal
sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    data object Error : MarsUiState
    data object Loading : MarsUiState
}

// VIEWMODEL: pide las fotos al repositorio y expone el estado a la vista.
class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {

    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    // Foto que se está viendo en grande (null = se muestra la cuadrícula)
    var fotoSeleccionada: MarsPhoto? by mutableStateOf(null)
        private set

    // Se llama al API apenas se crea el ViewModel
    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                MarsUiState.Success(marsPhotosRepository.getMarsPhotos())
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }

    fun verFoto(foto: MarsPhoto) {
        fotoSeleccionada = foto
    }

    fun cerrarFoto() {
        fotoSeleccionada = null
    }

    // Factory: el ViewModel recibe el repositorio desde el contenedor de la Application
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }
}
