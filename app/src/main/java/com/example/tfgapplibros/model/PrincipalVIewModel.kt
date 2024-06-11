package com.example.tfgapplibros.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    var endOfListReached = false

    private val _search = MutableLiveData<String>("")
    val search: LiveData<String> = _search

    fun changesearch(string: String) {
        _search.value = string
    }

    fun recogerLibros(excludeId: String) {
        if (loading || endOfListReached) return

        loading = true
        Log.d("Librooooos","empieza el loading")
            libroRepo.librosPrincipal(excludeId,lastDocumentSnapshot,limit).observeForever { newLibros ->
                if (newLibros.isNotEmpty()) {
                    lastDocumentSnapshot = newLibros.last().documentSnapshot
                    _libros.value = (_libros.value ?: emptyList()) + newLibros

                    if (newLibros.size < limit) {
                        endOfListReached = true
                    }
                } else {
                    endOfListReached = true
                }
                _libros.postValue(_libros.value)
                Handler(Looper.getMainLooper()).post{
                    loading = false
                    Log.d("Librooooos","acaba el loading")
                }

            }
    }

}