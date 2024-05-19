package com.example.tfgapplibros.data

import java.io.StringBufferInputStream

data class Usuario(
    val idUsuario : String,
    val nombre : String,
    val apellidos : String,
    val usuario : String,
    val contrasena : String,
    val edad : Int,
    val correo : String,
    val direccion : String,
    val pais : String,
    val ciudad : String,
    val codigoPostal : Int,
    val numeroTelefono : Int,
    val generos : List<String>,
    val fotoPerfil : String
)
