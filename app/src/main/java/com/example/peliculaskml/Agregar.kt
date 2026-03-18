package com.example.peliculaskml

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database

class Agregar : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("peliculas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        val setNombre = findViewById<EditText>(R.id.setNombre1)
        val setAnio    = findViewById<EditText>(R.id.setAnio1)
        val setGenero = findViewById<EditText>(R.id.setGenero1)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {

            val peliNueva = Pelicula()
            peliNueva.nombre = setNombre.text.toString()
            peliNueva.anio    = setAnio.text.toString()
            peliNueva.genero = setGenero.text.toString()

            myRef.push().setValue(peliNueva)
                .addOnSuccessListener {
                    Toast.makeText(this, "Película agregada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al agregar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}