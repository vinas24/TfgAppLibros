package com.example.tfgapplibros.data


data class Usuario(
    val idUsuario : String = "",
    val nombreUsuario : String = "",
    val correo : String = "",
    val contrasena : String = "",
    val nombre : String = "",
    val apellidos : String = "",
    val biografia : String = "",
    val pais : String = "",
    val ciudad : String = "",
    val numeroTelefono : Int = 1,
    val generos : List<String> = ArrayList<String>(),
    val fotoPerfil : String = ""
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "idUsuario" to this.idUsuario,
            "nombreUsuario" to this.nombreUsuario,
            "correo" to this.correo,
            "contrasena" to this.contrasena,
            "nombre" to this.nombre,
            "apellidos" to this.apellidos,
            "biografia" to this.biografia,
            "pais" to this.pais,
            "ciudad" to this.ciudad,
            "numeroTelefono" to this.numeroTelefono,
            "generos" to this.generos,
            "fotoPerfil" to this.fotoPerfil
        )
    }
}

