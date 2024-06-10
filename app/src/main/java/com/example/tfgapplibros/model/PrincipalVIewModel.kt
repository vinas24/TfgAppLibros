package com.example.tfgapplibros.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroPaginacion

import com.example.tfgapplibros.data.LibroRepository
import com.google.firebase.firestore.DocumentSnapshot

class PrincipalVIewModel: ViewModel() {
    private val libroRepo = LibroRepository()
    private val _libros = MutableLiveData<List<LibroPaginacion>>()
    val libros: LiveData<List<LibroPaginacion>> get() = _libros
    private var lastDocumentSnapshot: DocumentSnapshot? = null
    private val limit: Long = 10
    var loading = false

    fun recogerLibros(excludeId: String) {
        if (loading) return

        loading = true
        Log.d("Librooooos","Aqui he entrado al menos2")
            libroRepo.librosPrincipal(excludeId,lastDocumentSnapshot,limit).observeForever {
                lastDocumentSnapshot = if (it.isNotEmpty()) it.last().documentSnapshot else null
                _libros.value = (_libros.value ?: emptyList()) + it
                loading = false
                Log.d("Librooooos", _libros.value?.size.toString())
            }
    }

}