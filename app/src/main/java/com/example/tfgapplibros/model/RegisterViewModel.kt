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

    private val _nombre = MutableLiveData<String>()
    val nombre: LiveData<String> = _nombre

    private val _apellidos = MutableLiveData<String>()
    val apellidos: LiveData<String> = _apellidos

    private val _nombreUsuario = MutableLiveData<String>()
    val nombreUsuario: LiveData<String> = _nombreUsuario

    private val _contrasena = MutableLiveData<String>()
    val contrasena: LiveData<String> = _contrasena

    private val _edad = MutableLiveData<Int>()
    val edad: LiveData<Int> = _edad

    private val _correo = MutableLiveData<String>()
    val correo: LiveData<String> = _correo

    private val _direccion = MutableLiveData<String>()
    val direccion: LiveData<String> = _direccion

    private val _pais = MutableLiveData<String>()
    val pais: LiveData<String> = _pais

    private val _ciudad = MutableLiveData<String>()
    val ciudad: LiveData<String> = _ciudad

    private val _codigoPostal = MutableLiveData<Int>()
    val codigoPostal: LiveData<Int> = _codigoPostal

    private val _numeroTelefono = MutableLiveData<Int>()
    val numeroTelefono: LiveData<Int> = _numeroTelefono

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

    fun nombreChange(newNombre : String) {
        _nombre.value = newNombre
    }

    fun apellidosChange(newApellidos : String) {
        _apellidos.value = newApellidos
    }

    fun nombreUsuarioChange(newNombreUsuario : String) {
        _nombreUsuario.value = newNombreUsuario
    }

    fun contrasenaChange(newContrasena : String) {
        _contrasena.value = newContrasena
    }

    fun edadChange(newEdad : Int) {
        _edad.value = newEdad
    }

    fun correoChange(newCorreo : String){
        _correo.value = newCorreo
    }

    fun direccionChange(newDireccion : String){
        _direccion.value = newDireccion
    }

    fun ciudadChange(newCiudad : String){
        _ciudad.value = newCiudad
    }

    fun paisChange(newPais : String){
        _pais.value = newPais
    }

    fun codigoPostalChange(newCodigoPostal : Int){
        _codigoPostal.value = newCodigoPostal
    }

    fun numeroTelefonoChange(newNumeroTelefono : Int){
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
        val nombre = _nombre.value.orEmpty()
        val apellidos = _apellidos.value.orEmpty()
        val nombreUsuario = _nombreUsuario.value.orEmpty()
        val contrasena = _contrasena.value.orEmpty()
        val edad = _edad.value ?:1
        val correo = _correo.value.orEmpty()
        val direccion = _direccion.value.orEmpty()
        val pais = _pais.value.orEmpty()
        val ciudad = _ciudad.value.orEmpty()
        val codigoPostal = _codigoPostal.value ?:1
        val numeroTelefono = _numeroTelefono.value ?:1
        val generos = _generos.value?: emptyList()
        val fotoPerfil = _fotoPerfil.value

        if (nombre.isBlank() || apellidos.isBlank() || nombreUsuario.isBlank() || contrasena.isBlank() || edad == 0 || correo.isBlank()
            || direccion.isBlank() || pais.isBlank() || ciudad.isBlank() || codigoPostal == 0 || numeroTelefono == 0 || generos.isEmpty()
            || fotoPerfil == null) {
            _mensError.value = "Rellena todos los campos"
            return
        }

        val usuario = Usuario(
            nombre = nombre,
            apellidos = apellidos,
            nombreUsuario = nombreUsuario,
            contrasena = contrasena,
            edad = edad,
            correo = correo,
            direccion = direccion,
            pais = pais,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            generos = generos,
            numeroTelefono = numeroTelefono
        )
        usuarioRepository.guardarUsuario(usuario, fotoPerfil,
            onSuccess = {
                _mensOk.value = "Usuario anadido"
                perfil()
                _loading.value = false
            },
            onFailure = {
                _loading.value = false

                _mensError.value = "No se pudo guardar el usuario"
            })

    }

    fun actualizarUsuario(idUsuario: String, perfil: () -> Unit) {
        _loading.value = true
        val nombre = _nombre.value.orEmpty()
        val apellidos = _apellidos.value.orEmpty()
        val nombreUsuario = _nombreUsuario.value.orEmpty()
        val contrasena = _contrasena.value.orEmpty()
        val edad = _edad.value ?:1
        val correo = _correo.value.orEmpty()
        val direccion = _direccion.value.orEmpty()
        val pais = _pais.value.orEmpty()
        val ciudad = _ciudad.value.orEmpty()
        val codigoPostal = _codigoPostal.value ?:1
        val numeroTelefono = _numeroTelefono.value ?:1
        val generos = _generos.value.orEmpty()
        val fotoPerfil = _fotoPerfil.value

        if (nombre.isBlank() || apellidos.isBlank() || nombreUsuario.isBlank() || contrasena.isBlank() || edad == 0 || correo.isBlank()
            || direccion.isBlank() || pais.isBlank() || ciudad.isBlank() || codigoPostal == 0 || numeroTelefono == 0 || generos.isEmpty()
            || fotoPerfil == null) {
            _mensError.value = "Rellena todos los campos"
            return
        }

        val usuario = Usuario(
            idUsuario = idUsuario,
            nombre = nombre,
            apellidos = apellidos,
            nombreUsuario = nombreUsuario,
            contrasena = contrasena,
            edad = edad,
            correo = correo,
            direccion = direccion,
            pais = pais,
            ciudad = ciudad,
            codigoPostal = codigoPostal,
            generos = generos,
            numeroTelefono = numeroTelefono
        )
        usuarioRepository.actualizarUsuario(idUsuario, usuario, fotoPerfil,
            onSuccess = {
                _mensOk.value = "Usuario actualizado"
                perfil()
                _loading.value = false
            },
            onFailure = {
                _loading.value = false
                _mensError.value = "No se pudo actualizar el usuario"
            })
    }

    fun cargarDetallesUsuario(idUsuario: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val usuario = usuarioRepository.obtenerUsuarioPorId(idUsuario = idUsuario)
            if (usuario != null) {
                _nombre.postValue(usuario.nombre)
                _apellidos.postValue(usuario.apellidos)
                _nombreUsuario.postValue(usuario.nombreUsuario)
                _contrasena.postValue(usuario.contrasena)
                _edad.postValue(usuario.edad)
                _correo.postValue(usuario.correo)
                _direccion.postValue(usuario.direccion)
                _pais.postValue(usuario.pais)
                _ciudad.postValue(usuario.ciudad)
                _codigoPostal.postValue(usuario.codigoPostal)
                _numeroTelefono.postValue(usuario.numeroTelefono)
                _generos.postValue(usuario.generos)
                val fotoPerfil = usuario.fotoPerfil
                if (fotoPerfil.isNotEmpty()) {
                    _fotoPerfil.postValue(Uri.parse(fotoPerfil))
                }
                _loading.value = false
            } else {
                _mensError.value = "No se ha podido cargar la informacion del usuario"
                _loading.value = false
            }
        }

    }
}