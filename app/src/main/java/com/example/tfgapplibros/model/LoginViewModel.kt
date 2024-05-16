package com.example.tfgapplibros.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = mutableStateOf(false)
    val loading get() = _loading.value

    private val _loginError = MutableLiveData<String>(null)
    val loginError: LiveData<String> get() = _loginError

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
                        _loading.value = false
                        _loginError.value = "Error al iniciar sesion. Comprueba tus credenciales."
                    }
                }
        } catch (es: Exception) {
            _loginError.value = "Error al iniciar sesion. Vuelva a intentarlo."
        } finally {
            Log.d("loadingggg", _loading.value.toString())
        }

    }

}