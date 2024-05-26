package com.example.tfgapplibros.data

data class Libro(
    val userId: String = "",
    val libroId: String = "",
    val titulo: String = "",
    val autor: String = "",
    val genero: String = "",
    val estado: Int = 1,
    val imgUrl: String = "",
    val descripcion: String = "",

    ){
    /*la funcion de a continuacion es para la busqueda*/
    fun doesMatchSearchQuery(query:String) : Boolean{
        val matchingCombinations = listOf(
            "$titulo",
            "$autor",
            "$genero",
            "$autor $genero",
            "$autor $titulo",
            "$titulo $autor",
            "${autor.first()} ${titulo.first()}",
            "${titulo.first()}",
            "${autor.first()}"


        )
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}
