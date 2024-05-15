package com.example.tfgapplibros.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import kotlin.math.log

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

    suspend fun librosDelUsuario(userId: String): List<Libro> {
        val libros = mutableListOf<Libro>()
        try {
            val snapshot = db
                .collection("usuarios")
                .document(userId)
                .collection("libros")
                .get()
                .await()
            for (doc in snapshot.documents) {
                val libro = doc.toObject(Libro::class.java)
                libro?.let { libros.add(libro) }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return libros
    }


}