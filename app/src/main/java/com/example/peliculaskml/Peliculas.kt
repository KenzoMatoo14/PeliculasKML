package com.example.peliculaskml

import com.google.firebase.database.PropertyName

class Peliculas(nom: String?, ani: String?, gen: String?, ids: String?) {
    var nombre: String? = nom

    var anio: String? = ani
    var genero: String? = gen
    var id: String? = ids
}