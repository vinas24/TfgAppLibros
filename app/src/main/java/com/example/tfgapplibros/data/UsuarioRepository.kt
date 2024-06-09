package com.example.tfgapplibros.data

import android.net.Uri
import android.util.Log
import com.example.tfgapplibros.model.Autentificacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UsuarioRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    fun crearUsuario(correo : String, password : String, onSuccess : (String) -> Unit, onFailure : (Exception) -> Unit ){
        Autentificacion.firebaseAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener{
                task ->
            if(task.isSuccessful){
                var userId = Autentificacion.usuarioActualUid!!
                onSuccess(userId)
            } else {
                task.exception?.let{
                    exception -> onFailure(exception)
                }
            }
        }
    }

    fun guardarUsuario(
        usuId: String,
        usuario: Usuario,
        fotoPerfil: Uri?,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val imgRef = storage.reference.child("usuarios/$usuId.jpg")

        if (fotoPerfil != null) {
            imgRef.putFile(fotoPerfil)
                .addOnSuccessListener {
                    imgRef.downloadUrl.addOnSuccessListener {
                        val usuarioActualizado = usuario.copy(fotoPerfil = it.toString(), idUsuario = usuId)
                        guardarUsuarioDb(usuId, usuarioActualizado, onSuccess, onFailure)
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
    //TODO: Esto se realizará en un futuro hopefuully
//    fun actualizarUsuario(
//        idUsuario: String,
//        usuario: Usuario,
//        fotoPerfil: Uri,
//        onSuccess: () -> Unit,
//        onFailure: (Exception) -> Unit
//    ) {
//        val imgRef = storage.reference.child("usuarios/$idUsuario.jpg")
//        imgRef.delete()
//            .addOnSuccessListener {
//                //Esta uri esta mal
//                imgRef.putFile(fotoPerfil)
//                    .addOnSuccessListener {
//                        imgRef.downloadUrl.addOnSuccessListener {
//                            val usuarioActualizado =
//                                usuario.copy(fotoPerfil = it.toString(), idUsuario = idUsuario)
//                            guardarUsuarioDb(idUsuario, usuarioActualizado, onSuccess, onFailure)
//                        }.addOnFailureListener { onFailure(it) }
//                    }.addOnFailureListener { onFailure(it) }
//            }.addOnFailureListener { onFailure(it) }
//    }


    suspend fun obtenerUsuarioPorId(idUsuario: String): Usuario? {
        return try {
            val document = db
                .collection("usuarios")
                .document(idUsuario)
                .get()
                .await()
            if (document.exists()) {
                var user = document.toObject(Usuario::class.java)
                Log.d("userrrrrr", user.toString())
                user

            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    //TODO: Posible extra si da tiempo
//    fun borrarUsuario(
//        idUsuario : String,
//        fotoPerfil : String,
//        onSuccess: () -> Unit,
//        onFailure: (Exception) -> Unit
//    ) {
//        val usuarioRef =
//            db.collection("usuarios").document(idUsuario)
//        val imgRef = storage.getReferenceFromUrl(fotoPerfil)
//
//        usuarioRef.delete()
//            .addOnSuccessListener {
//                imgRef.delete()
//                    .addOnSuccessListener { onSuccess() }
//                    .addOnFailureListener { onFailure(it) }
//            }.addOnFailureListener { onFailure(it) }
//    }
}
