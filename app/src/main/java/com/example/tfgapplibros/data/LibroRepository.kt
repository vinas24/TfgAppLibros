package com.example.tfgapplibros.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

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
                        val libroActualizado = libro.copy(imgUrl = it.toString(), libroId = libroId)
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

    suspend fun obtenerLibroPorId(libroId: String, userId: String): Libro? {
        return try {
            val doc = db
                .collection("usuarios")
                .document(userId)
                .collection("libros")
                .document(libroId)
                .get()
                .await()
            if (doc.exists()) {
                val libro = doc.toObject(Libro::class.java)

                libro
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun borrarLibro(userId: String, libroId: String, imgUrl: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val libroRef =
            db.collection("usuarios").document(userId).collection("libros").document(libroId)
        val imgRef = storage.getReferenceFromUrl(imgUrl)

        libroRef.delete()
            .addOnSuccessListener {
                imgRef.delete()
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener {onFailure(it)}
            }.addOnFailureListener {onFailure(it)}
    }
}