package com.example.tfgapplibros.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Autentificacion {
    val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }

    val usuarioActualUid: String?
        get() = firebaseAuth.currentUser?.uid

    fun logout(onComplete: () -> Unit) {
        firebaseAuth.signOut();
        if (firebaseAuth.currentUser == null) {
            onComplete()
        }
    }

    fun checkUsuarioGuardado(): Boolean {
        return firebaseAuth.currentUser != null
    }
}