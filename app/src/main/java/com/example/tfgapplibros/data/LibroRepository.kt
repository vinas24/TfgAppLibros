package com.example.tfgapplibros.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
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
        val libroId = db
            .collection("usuarios")
            .document(userId).collection("libros")
            .document().id
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

    fun actualizarLibro(
        userId: String,
        libroId: String,
        libro: Libro,
        imgUri: Uri,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val imgRef = storage.reference.child("libros/$libroId.jpg")
        imgRef.delete()
            .addOnSuccessListener {
                //Esta uri esta mal
                //ª q bn
                imgRef.putFile(imgUri)
                    .addOnSuccessListener {
                        imgRef.downloadUrl.addOnSuccessListener {
                            val libroActualizado =
                                libro.copy(imgUrl = it.toString(), libroId = libroId)
                            guardarLibroDb(userId, libroId, libroActualizado, onSuccess, onFailure)
                        }.addOnFailureListener { onFailure(it) }
                    }.addOnFailureListener { onFailure(it) }
            }.addOnFailureListener { onFailure(it) }


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

    fun borrarLibro(
        userId: String,
        libroId: String,
        imgUrl: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val libroRef =
            db.collection("usuarios").document(userId).collection("libros").document(libroId)
        val imgRef = storage.getReferenceFromUrl(imgUrl)

        libroRef.delete()
            .addOnSuccessListener {
                imgRef.delete()
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it) }
            }.addOnFailureListener { onFailure(it) }
    }

    fun librosPrincipal(excludeId: String, lastDocumentSnapshot: DocumentSnapshot? = null, limit: Long = 10): LiveData<List<LibroPaginacion>> {
        val librosLiveData = MutableLiveData<List<LibroPaginacion>>()
        val librosCollection = db.collectionGroup("libros")

        //librosCollection.whereNotEqualTo("userId", excludeId).get()
            var query =  librosCollection.limit(limit)
                lastDocumentSnapshot?.let {
                    query = query.startAfter(it)
                }
            query.get().addOnSuccessListener { querySnapshot ->
            val libros = querySnapshot.documents.mapNotNull {docSnapshot ->
                val libro = docSnapshot.toObject(Libro::class.java)
                libro?.let { LibroPaginacion(it, docSnapshot) }
            }
            librosLiveData.value = libros
        }.addOnFailureListener {
            //TODO: Que hacer en caso de error
        }
        return librosLiveData
    }
}