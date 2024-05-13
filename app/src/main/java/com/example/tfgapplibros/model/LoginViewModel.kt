package com.example.tfgapplibros.model

import android.util.Log
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
    private val _loading = MutableLiveData(false)
    private val _loginError = MutableLiveData<String?>()

    fun signInConCorreoContrasena(email: String, passwd: String, principal: ()-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email,passwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        principal()
                    } else {
                        _loginError.value = "Error al iniciar sesion. Comprueba tus credenciales."

                    }
                }
        } catch (es: Exception) {
            _loginError.value = "Error al iniciar sesion. Vuelva a intentarlo."
        }

    }

}