package com.example.tfgapplibros.data

data class Libro(
    val userId: String,
    val titulo: String,
    val autor: String,
    val genero: String,
    val estado: Int,
    val imgUrl: String = "",
    val descripcion: String,

    )
