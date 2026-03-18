package com.example.peliculaskml

import PeliAdapter
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Home : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val database = Firebase.database
    val myRef = database.getReference("peliculas")
    lateinit var datos: ArrayList<Peliculas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val botonAgregar = findViewById<FloatingActionButton>(R.id.botonAgregar)
        botonAgregar.setOnClickListener {
            startActivity(Intent(this, Agregar::class.java))
        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datos = ArrayList()
                snapshot.children.forEach { hijo ->
                    val nombre = hijo.child("nombre").value?.toString() ?: ""
                    val genero = hijo.child("genero").value?.toString() ?: ""
                    val id     = hijo.key.toString()
                    val anio   = hijo.child("anio").value?.let { "$it" } ?: ""

                    datos.add(Peliculas(nombre, anio, genero, id))
                }

                val lista = findViewById<ListView>(R.id.lista)
                val adaptador = PeliAdapter(this@Home, datos)
                lista.adapter = adaptador

                lista.setOnItemClickListener { _, _, position, _ ->
                    startActivity(
                        Intent(this@Home, Detalle::class.java)
                            .putExtra("key", datos[position].id)
                            .putExtra("nombre", datos[position].nombre.toString())
                            .putExtra("anio", datos[position].anio.toString())
                            .putExtra("genero", datos[position].genero.toString())
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Se llama si la lectura falla (ej. reglas de seguridad)
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun llenaLista() {
        val adaptador = PeliAdapter(this, datos)
        val lista = findViewById<ListView>(R.id.lista)
        lista.adapter = adaptador
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout  ) {
            Toast.makeText(this, "opción Cerrar", Toast.LENGTH_LONG).show()
            cerrarSesion()
        } else if (item.itemId == R.id.perfil) {
            Toast.makeText(this, "opción Perfil", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun cerrarSesion() {
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
