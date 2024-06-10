package com.example.tfgapplibros.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroPaginacion
import com.example.tfgapplibros.data.LibroRepository
import com.example.tfgapplibros.data.UsuarioRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PrincipalVIewModel: ViewModel() {
    private val libroRepo = LibroRepository()
    private val _libros = MutableLiveData<List<Libro>>()
    val libros: LiveData<List<Libro>> get() = _libros

    var loading = false

    fun recogerLibros(excludeId: String) {
        if (loading) return

        loading = true
        Log.d("Librooooos","Aqui he entrado al menos2")
            libroRepo.librosPrincipal(excludeId).observeForever {
                _libros.value = it
                loading = false
                Log.d("Librooooos", _libros.value?.size.toString())
            }
    }


}