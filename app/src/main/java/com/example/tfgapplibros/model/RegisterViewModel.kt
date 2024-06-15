package com.example.tfgapplibros.model

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tfgapplibros.data.Libro
import com.example.tfgapplibros.data.LibroRepository
import com.example.tfgapplibros.data.Usuario
import com.example.tfgapplibros.data.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.attribute.UserPrincipal

class RegisterViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository()

    private val _nombreUsuario = MutableLiveData<String>()
    val nombreUsuario: LiveData<String> = _nombreUsuario

    private val _contrasena = MutableLiveData<String>()
    val contrasena: LiveData<String> = _contrasena

    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> = _correo

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellidos = MutableLiveData<String>()
    val apellidos: LiveData<String> = _apellidos

    private val _biografia = MutableLiveData<String>()
    val biografia: LiveData<String> = _biografia

    private val _pais = MutableLiveData<String>()
    val pais: LiveData<String> = _pais

    private val _ciudad = MutableLiveData<String>()
    val ciudad: LiveData<String> = _ciudad

    private val _numeroTelefono = MutableLiveData<Long>()
    val numeroTelefono: LiveData<Long> = _numeroTelefono

    private val _generos = MutableLiveData<List<String>>()
    val generos: LiveData<List<String>> = _generos

    private val _fotoPerfil = MutableLiveData<Uri?>()
    val fotoPerfil: LiveData<Uri?> = _fotoPerfil

    private val _loading = mutableStateOf(false)

    private val _mensError = MutableLiveData<String?>()
    val mensError: LiveData<String?> = _mensError

    private val _mensOk = MutableLiveData<String?>()
    val mensOk: LiveData<String?> = _mensOk

    val loading get() = _loading.value

    fun nombreUsuarioChange(newNombreUsuario : String) {
        _nombreUsuario.value = newNombreUsuario
    }

    fun contrasenaChange(newContrasena : String) {
        _contrasena.value = newContrasena
    }

    fun correoChange(newCorreo : String){
        _correo.value = newCorreo
    }

    fun nombreChange(newNombre : String) {
        _nombre.value = newNombre
    }

    fun apellidosChange(newApellidos : String) {
        _apellidos.value = newApellidos
    }

    fun biografiaChange(newBiografia : String){
        _biografia.value = newBiografia
    }

    fun ciudadChange(newCiudad : String){
        _ciudad.value = newCiudad
    }

    fun paisChange(newPais : String){
        _pais.value = newPais
    }

    fun numeroTelefonoChange(newNumeroTelefono : Long){
        _numeroTelefono.value = newNumeroTelefono
    }

    fun generosChange(newGeneros : List<String>){
        _generos.value = newGeneros
    }

    fun fotoPerfilChange(newFotoPerfil: Uri) {
        _fotoPerfil.value = newFotoPerfil
        Log.d("Nueva Foto de Perfil",_fotoPerfil.value.toString())

    }

    fun registerUser(perfil: () -> Unit) {
        _loading.value=true
        val nombreUsuario = _nombreUsuario.value.orEmpty()
        val contrasena = _contrasena.value.orEmpty()
        val correo = _correo.value.orEmpty()
        val nombre = _nombre.value.orEmpty()
        val apellidos = _apellidos.value.orEmpty()
        val biografia = _biografia.value.orEmpty()
        val pais = _pais.value.orEmpty()
        val ciudad = _ciudad.value.orEmpty()
        val numeroTelefono = _numeroTelefono.value ?:1
        val generos = _generos.value?: emptyList()
        val fotoPerfil = _fotoPerfil.value

        if (nombreUsuario.isBlank() || contrasena.isBlank() || correo.isBlank() || nombre.isBlank() || apellidos.isBlank()
            ||biografia.isBlank() || pais.isBlank() || ciudad.isBlank() || numeroTelefono == 0L || generos.isEmpty()
            || fotoPerfil == null) {
            _mensError.value = "Rellena todos los campos"
            return
        }

        val usuario = Usuario(
            nombreUsuario = nombreUsuario,
            contrasena = contrasena,
            correo = correo,
            nombre = nombre,
            apellidos = apellidos,
            biografia = biografia,
            pais = pais,
            ciudad = ciudad,
            generos = generos,
            numeroTelefono = numeroTelefono
        )
        usuarioRepository.crearUsuario(correo, contrasena,
            onSuccess = {
                _mensOk.value = "Usuario creado"
                usuarioRepository.guardarUsuario(it, usuario, fotoPerfil,
                    onSuccess = {
                        _mensOk.value = "Usuario guardado"
                        perfil()
                        _loading.value = false
                    },
                    onFailure = {
                        _loading.value = false
                        _mensError.value = "No se pudo guardar el usuario"
                    })
            },
            onFailure = {
                _loading.value = false

                _mensError.value = "No se pudo crear el usuario"
            })

    }
}