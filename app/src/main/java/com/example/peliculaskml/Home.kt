package com.example.peliculaskml

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Home : AppCompatActivity() {

    var EXTRA_NAME: String? = null
    var parametros: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        parametros = findViewById<TextView>(R.id.parametros)
        EXTRA_NAME = intent.getStringExtra("correo")
        parametros?.text = "Parametros del usuario: \n" + EXTRA_NAME

        val regresahome = findViewById<Button>(R.id.SignOut)

        regresahome.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }
    }
}