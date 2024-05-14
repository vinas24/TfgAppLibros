package com.example.tfgapplibros.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class LibroRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun guardarLibro(
        userId: String,
        libro: Libro,
        imgUri: Uri?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val libroId = db.collection("usuarios").document(userId).collection("libros").document().id
        val imgRef = storage.reference.child("libros/$libroId.jpg")

        if (imgUri != null) {
            imgRef.putFile(imgUri)
                .addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener {
                        val libroActualizado = libro.copy(imgUrl = it.toString())
                        guardarLibroDb(userId, libroId, libroActualizado, onSuccess, onFailure)
                    }.addOnFailureListener { onFailure(it) }
                }.addOnFailureListener { onFailure(it) }
        }
    }

    private fun guardarLibroDb(
        userId: String,
        libroId: String,
        libro: Libro,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("usuarios")
            .document(userId)
            .collection("libros")
            .document(libroId)
            .set(libro)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }

    }
}