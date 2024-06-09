package com.example.tfgapplibros.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = mutableStateOf(false)
    val loading get() = _loading.value

    private val _loginError = mutableStateOf("")
    val loginError get() = _loginError.value



    fun signInConCorreoContrasena(email: String, passwd: String, principal: ()-> Unit)
    = viewModelScope.launch {
        try {
            _loading.value = true
            Log.d("loadingggg", _loading.value.toString())
            auth.signInWithEmailAndPassword(email,passwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loading.value = false
                        principal()
                    } else {
                        _loginError.value = "Error al iniciar sesion. Comprueba tus credenciales."
                        _loading.value = false
                    }
                }
        } catch (es: Exception) {
            _loginError.value = "Error al iniciar sesion. Vuelva a intentarlo."
        }
    }

    fun resetLoginError() {
        _loginError.value = ""
    }
}