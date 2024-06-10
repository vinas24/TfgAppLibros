package com.example.tfgapplibros.data

import com.google.firebase.firestore.DocumentSnapshot

data class LibroPaginacion(
    val libro: Libro,
    val documentSnapshot: DocumentSnapshot
)
