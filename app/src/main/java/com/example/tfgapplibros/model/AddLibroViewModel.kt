package com.example.tfgapplibros.model

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroRepository

class AddLibroViewModel : ViewModel() {

    private val libroRepo = LibroRepository()

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo

    private val _autor = MutableLiveData<String>()
    val autor: LiveData<String> = _autor

    private val _genero = MutableLiveData<String>()
    val genero: LiveData<String> = _genero

    private val _estado = MutableLiveData<Int>()
    val estado: LiveData<Int> = _estado

    private val _descripcion = MutableLiveData<String>()
    val descripcion: LiveData<String> = _descripcion

    private val _imgUri = MutableLiveData<Uri?>()
    val imgUri: LiveData<Uri?> = _imgUri

    private val _mensError = MutableLiveData<String?>()
    val mensError: LiveData<String?> = _mensError

    private val _mensOk = MutableLiveData<String?>()
    val mensOk: LiveData<String?> = _mensOk

    fun tituloChange(newTit: String) {
        _titulo.value = newTit
    }

    fun autorChange(newAut: String) {
        _autor.value = newAut
    }

    fun generoChange(newGen: String) {
        _genero.value = newGen
    }

    fun estadoChange(newEst: Int) {
        _estado.value = newEst
    }

    fun descripcionChange(newDesc: String) {
        _descripcion.value = newDesc
    }

    fun newImagenSelec(newUri: Uri) {
        _imgUri.value = newUri
    }

    fun guardarLibro(userId: String, perfil: () -> Unit) {
        val titulo = _titulo.value.orEmpty()
        val autor = _autor.value.orEmpty()
        val genero = _genero.value.orEmpty()
        val estado = _estado.value ?: 1
        val desc = _descripcion.value.orEmpty()
        val img = _imgUri.value

        if (titulo.isBlank() || autor.isBlank()) {
            _mensError.value = "Rellena todos los campos"
            return
        }

        val libro = Libro(
            userId = userId,
            titulo = titulo,
            autor = autor,
            genero = genero,
            estado = estado,
            descripcion = desc
        )
        libroRepo.guardarLibro(userId, libro, img,
            onSuccess = {
                _mensOk.value = "Libro anadido"
                perfil()
            },
            onFailure = {
                _mensError.value = "No se pudo guardar el libro"
            })

    }

}