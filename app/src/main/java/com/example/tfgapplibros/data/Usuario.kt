package com.example.tfgapplibros.data


data class Usuario(
    val idUsuario : String = "",
    val nombre : String = "",
    val apellidos : String = "",
    val nombreUsuario : String = "",
    val correo : String = "",
    val contrasena : String = "",
    val edad : Int = 1,
    val direccion : String = "",
    val pais : String = "",
    val ciudad : String = "",
    val codigoPostal : Int = 1,
    val numeroTelefono : Int = 1,
    val generos : List<String> = ArrayList<String>(),
    val fotoPerfil : String = ""
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "idUsuario" to this.idUsuario,
            "nombre" to this.nombre,
            "apellidos" to this.apellidos,
            "nombreUsuario" to this.nombreUsuario,
            "correo" to this.correo,
            "contrasena" to this.contrasena,
            "edad" to this.edad,
            "direccion" to this.direccion,
            "pais" to this.pais,
            "ciudad" to this.ciudad,
            "codigoPostal" to this.codigoPostal,
            "numeroTelefono" to this.numeroTelefono,
            "generos" to this.generos,
            "fotoPerfil" to this.fotoPerfil
        )
    }
}

