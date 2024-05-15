package com.example.tfgapplibros.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LibroViewModel : ViewModel() {
    private val libroRepo = LibroRepository()
    fun obtenerLibro(libroId: String?, userId: String?, onComplete: (Libro?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (libroId != null && userId != null) {
                val libro = libroRepo.obtenerLibroPorId(libroId, userId)
                onComplete(libro)
            }
        }
    }
}