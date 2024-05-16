package com.example.tfgapplibros.model

import androidx.compose.foundation.Image
import androidx.compose.runtime.saveable.autoSaver
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

object Autentificacion {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    val usuarioActualUid: String?
        get() = firebaseAuth.currentUser?.uid

    fun logout( onComplete: () -> Unit ){
        firebaseAuth.signOut();
        if (firebaseAuth.currentUser == null) {
            onComplete()
        }
    }
}