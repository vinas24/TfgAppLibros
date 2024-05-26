package com.example.tfgapplibros.data

import java.io.StringBufferInputStream

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
)
