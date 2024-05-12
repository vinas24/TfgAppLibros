package com.example.tfgapplibros.model

import androidx.compose.runtime.collection.MutableVector
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navOptions
import com.example.tfgapplibros.views.Principal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInConCorreoContrasena(email: String, passwd: String, principal: ()-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email,passwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        principal()
                        //Aqui deberia mandar a la pantalla principal
                    } else {
                        //Usuario no ha podido Logear
                    }
                }
        } catch (es: Exception) {
            //Error cualquiera
        }
    }

}