package com.example.tfgapplibros.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UsuarioRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
/*
    suspend fun crearUsuario(usuario: Usuario, password: String): Result<Usuario> {
        return try {
            val authResult = db.createUserWithEmailAndPassword(usuario.correo, password).await()
            val userId = authResult.user?.uid ?: throw Exception("User ID is null")
            val usuarioConId = usuario.copy(idUsuario = userId)
            firestore.collection("usuarios").document(userId).set(usuarioConId).await()
            Result.success(usuarioConId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

 */
    fun guardarUsuario(
        usuario: Usuario,
        fotoPerfil: Uri?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val idUsuario = db
            .collection("usuarios")
            .document().id
        val imgRef = storage.reference.child("usuarios/$idUsuario.jpg")

        if (fotoPerfil != null) {
            imgRef.putFile(fotoPerfil)
                .addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener {
                        val usuarioActualizado = usuario.copy(fotoPerfil = it.toString(), idUsuario = idUsuario)
                        guardarUsuarioDb(idUsuario, usuarioActualizado, onSuccess, onFailure)
                    }.addOnFailureListener { onFailure(it) }
                }.addOnFailureListener { onFailure(it) }
        }
    }

    private fun guardarUsuarioDb(
        idUsuario: String,
        usuario: Usuario,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("usuarios")
            .document(idUsuario)
            .set(usuario)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun actualizarUsuario(
        idUsuario: String,
        usuario: Usuario,
        fotoPerfil: Uri,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val imgRef = storage.reference.child("usuarios/$idUsuario.jpg")
        imgRef.delete()
            .addOnSuccessListener {
                //Esta uri esta mal
                imgRef.putFile(fotoPerfil)
                    .addOnSuccessListener {
                        imgRef.downloadUrl.addOnSuccessListener {
                            val usuarioActualizado =
                                usuario.copy(fotoPerfil = it.toString(), idUsuario = idUsuario)
                            guardarUsuarioDb(idUsuario, usuarioActualizado, onSuccess, onFailure)
                        }.addOnFailureListener { onFailure(it) }
                    }.addOnFailureListener { onFailure(it) }
            }.addOnFailureListener { onFailure(it) }
    }

    suspend fun obtenerUsuarioPorId(idUsuario: String): Usuario? {
        return try {
            val document = db
                .collection("usuarios")
                .document(idUsuario)
                .get()
                .await()
            if (document.exists()) {
                val usuario = document.toObject(Usuario::class.java)

                usuario
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun borrarUsuario(
        idUsuario : String,
        fotoPerfil : String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val usuarioRef =
            db.collection("usuarios").document(idUsuario)
        val imgRef = storage.getReferenceFromUrl(fotoPerfil)

        usuarioRef.delete()
            .addOnSuccessListener {
                imgRef.delete()
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it) }
            }.addOnFailureListener { onFailure(it) }
    }
}
