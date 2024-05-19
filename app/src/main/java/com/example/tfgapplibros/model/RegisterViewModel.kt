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
import com.example.tfgapplibros.data.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.nio.file.attribute.UserPrincipal

class RegisterViewModel : ViewModel() {

    private val _loginError = mutableStateOf("")
    val registerError get() = _loginError.value

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellidos = MutableLiveData<String>()
    val apellidos: LiveData<String> = _apellidos

    private val _usuario = MutableLiveData<String>()
    val usuario: LiveData<String> = _usuario

    private val _contrasena = MutableLiveData<String>()
    val contrasena: LiveData<String> = _contrasena

    private val _edad = MutableLiveData<Int?>()
    val edad: LiveData<Int?> = _edad

    private val _correo = MutableLiveData<String?>()
    val correo: LiveData<String?> = _correo

    private val _direccion = MutableLiveData<String?>()
    val direccion: LiveData<String?> = _direccion

    private val _pais = MutableLiveData<String?>()
    val pais: LiveData<String?> = _pais

    private val _ciudad = MutableLiveData<String?>()
    val ciudad: LiveData<String?> = _ciudad

    private val _codigoPostal = MutableLiveData<Int?>()
    val codigoPostal: LiveData<Int?> = _codigoPostal

    private val _numeroTelefono = MutableLiveData<Int?>()
    val numeroTelefono: LiveData<Int?> = _numeroTelefono

    private val _generos = MutableLiveData<String?>()
    val generos: LiveData<String?> = _generos

    private val _fotoPerfil = MutableLiveData<Uri?>()
    val fotoPerfil: LiveData<Uri?> = _fotoPerfil

    private val _loading = mutableStateOf(false)
    val loading get() = _loading.value

    fun nombreChange(newNombre: String) {
        _nombre.value = newNombre
    }

    fun apellidosChange(newApellidos: String) {
        _apellidos.value = newApellidos
    }

    fun usuarioChange(newUsuario: String) {
        _usuario.value = newUsuario
    }

    fun contrasenaChange(newContrasena: String) {
        _contrasena.value = newContrasena
    }

    fun edadChange(newEdad: Int) {
        _edad.value = newEdad
    }

    fun fotoPerfilChange(newFotoPerfil: Uri) {
        _fotoPerfil.value = newFotoPerfil
        Log.d("Nueva Foto de Perfil",_fotoPerfil.value.toString())

    }

    private val _mensError = MutableLiveData<String?>()
    val mensError: LiveData<String?> = _mensError

    private val _mensOk = MutableLiveData<String?>()
    val mensOk: LiveData<String?> = _mensOk

    fun registerUser(idUsuario : String, principal: () -> Unit) {
        _loading.value=true
        val nombre = _nombre.value.orEmpty()
        val apellidos = _apellidos.value.orEmpty()
        val usuario = _usuario.value.orEmpty()
        val contrasena = _contrasena.value.orEmpty()
        val edad = _edad.value ?:0
        val correo = _correo.value.orEmpty()
        val direccion = _direccion.value.orEmpty()
        val pais = _pais.value.orEmpty()
        val ciudad = _ciudad.value.orEmpty()
        val codigoPostal = _codigoPostal.value ?:0
        val numeroTelefono = _numeroTelefono.value ?:0
        val fotoPerfil = _fotoPerfil.value

        if (nombre.isBlank() || apellidos.isBlank() || usuario.isBlank() || contrasena.isBlank() || edad == 0 || correo.isBlank()
            || direccion.isBlank() || pais.isBlank() || ciudad.isBlank() || codigoPostal == 0 || numeroTelefono == 0) {
            _mensError.value = "Rellena todos los campos"
            return
        }

        val Usuario = Usuario(
            idUsuario = idUsuario,
            nombre = nombre,
            apellidos = apellidos,
            usuario = usuario,
            contrasena = contrasena,
            edad = edad,
            correo = correo,
            direccion = direccion,
            pais = pais,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            numeroTelefono = numeroTelefono,
            fotoPerfil = fotoPerfil
        )
    }
}