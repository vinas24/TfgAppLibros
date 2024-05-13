package com.example.tfgapplibros.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Autentificacion {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    val usuarioActualUid: String?
        get() = firebaseAuth.currentUser?.uid

    // You can add more authentication-related methods here
}