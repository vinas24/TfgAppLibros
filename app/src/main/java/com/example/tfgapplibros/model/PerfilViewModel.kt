package com.example.tfgapplibros.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class PerfilViewModel() : ViewModel(){

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros

    fun obtenerLibros(userId: String) {
        viewModelScope.launch {
            val libros = librosDelUsuario(userId)
            _libros.value = libros
        }
    }

    private suspend fun librosDelUsuario(userId: String): List<Libro> {
        val db = FirebaseFirestore.getInstance()
        val libros = mutableListOf<Libro>()
        try {
            val snapshot = db
                .collection("usuarios")
                .document(userId)
                .collection("libros")
                .get()
                .await()
            Log.d("libroespec",snapshot.documents.size.toString())

            for (doc in snapshot.documents) {
                val libro = doc.toObject(Libro::class.java)
                if (libro != null) {
                    libros.add(libro)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return libros
    }
}