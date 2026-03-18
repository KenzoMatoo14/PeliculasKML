package com.example.peliculaskml

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database

class Detalle : AppCompatActivity() {

    val database = Firebase.database
    val myRef = database.getReference("peliculas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        val key    = intent.getStringExtra("key")
        val nombre = intent.getStringExtra("nombre")
        val anio   = intent.getStringExtra("anio")
        val genero = intent.getStringExtra("genero")

        val setNombre = findViewById<EditText>(R.id.setNombre)
        val setAnio   = findViewById<EditText>(R.id.setAnio)
        val setGenero = findViewById<EditText>(R.id.setGenero)

        setNombre.setText(nombre)
        setAnio.setText(anio)
        setGenero.setText(genero)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {

            val peliEditada = Pelicula()
            peliEditada.nombre = setNombre.text.toString()
            peliEditada.anio = setAnio.text.toString()
            peliEditada.genero = setGenero.text.toString()

            myRef.child(key.toString()).setValue(peliEditada)
                .addOnSuccessListener {
                    Toast.makeText(this, "Película actualizada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
        }

        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        btnEliminar.setOnClickListener {
            myRef.child(key.toString()).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Película eliminada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}