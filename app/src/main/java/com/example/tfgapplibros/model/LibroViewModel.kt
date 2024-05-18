package com.example.tfgapplibros.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibroViewModel : ViewModel() {
    private val libroRepo = LibroRepository()

    private val _mensError = MutableLiveData<String?>()
    val mensError: LiveData<String?> = _mensError

    private val _mensOk = MutableLiveData<String?>()
    val mensOk: LiveData<String?> = _mensOk

    private val _loading = mutableStateOf(false)
    val loading get() = _loading.value

    fun obtenerLibro(libroId: String?, userId: String?, onComplete: (Libro?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (libroId != null && userId != null) {
                val libro = libroRepo.obtenerLibroPorId(libroId, userId)
                onComplete(libro)
            }
        }
    }

    fun borrarLibro(libro: Libro, perfil: () -> Unit) {
        _loading.value = true
        libroRepo.borrarLibro(
            libro.userId,
            libro.libroId,
            libro.imgUrl,
            onSuccess = {
                _mensOk.value = "Libro borrado correctamente"
                perfil()
                _loading.value = false

            },
            onFailure = {
                _mensError.value = "No se ha podido borrar el libro: $it"
                _loading.value = false
            })
    }
}