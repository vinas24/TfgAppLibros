package com.example.tfgapplibros.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroRepository
import com.example.tfgapplibros.data.Usuario
import com.example.tfgapplibros.data.UsuarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log

class PerfilViewModel() : ViewModel(){

    private val usuarioRepo = UsuarioRepository()

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros

    private val _favoritos = MutableStateFlow<List<Libro>>(emptyList())
    val favoritos: StateFlow<List<Libro>> = _favoritos

    private val _interesados = MutableStateFlow<List<Usuario>>(emptyList())
    val interesados: StateFlow<List<Usuario>> = _interesados

    private val _datosUser = MutableStateFlow<Usuario?>(null)

    val datosUser: StateFlow<Usuario?> = _datosUser

    fun obtenerLibros(userId: String) {
        viewModelScope.launch {
            val libros = librosDelUsuario(userId)
            _libros.value = libros
        }
    }
    fun obtenerFavoritos(userId: String) {
        viewModelScope.launch {
            val favs = librosFavoritos(userId)
            _favoritos.value = favs
        }
    }

    private suspend fun librosFavoritos(userId: String): List<Libro> {
        val db = FirebaseFirestore.getInstance()
        val libros = mutableListOf<Libro>()
        try {
            val snapshot = db
                .collection("usuarios")
                .document(userId)
                .collection("favoritos")
                .get()
                .await()

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


    fun cargarPerfil(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val user = usuarioRepo.obtenerUsuarioPorId(userId)
            if (user != null) {
                _datosUser.value = user
            }
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

    fun obtenerInteresados(userId: String) {
        val db = FirebaseFirestore.getInstance()
        val inters = mutableListOf<Usuario>()
        try {
            db
                .collection("usuarios")
                .document(userId)
                .collection("interesados")
                .get()
                .addOnSuccessListener {
                    for (doc in it.documents) {
                        val usuario = doc.toObject(Usuario::class.java)
                        if (usuario != null) {
                            db
                                .collection("usuarios")
                                .document(usuario.idUsuario)
                                .collection("interesados")
                                .document(userId)
                                .get()
                                .addOnSuccessListener {
                                    if (it.exists()) {
                                        inters.add(usuario)
                                        _interesados.value = inters
                                    }
                                }
                        }
                    }
                }
                .addOnFailureListener {
                }
        } catch (e: Exception) {
               e.printStackTrace()
        }
    }
}