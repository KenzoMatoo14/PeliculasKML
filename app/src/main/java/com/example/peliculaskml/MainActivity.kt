    package com.example.peliculaskml

    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.auth
    import com.google.firebase.Firebase
    import com.google.firebase.auth.FirebaseUser

    class MainActivity : AppCompatActivity() {
        private lateinit var auth: FirebaseAuth

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()

            setContentView(R.layout.activity_main)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            auth = Firebase.auth

            val login = findViewById<Button>(R.id.button)
            val emailEdit = findViewById<EditText>(R.id.editTextTextEmailAddress)
            val passwordEdit = findViewById<EditText>(R.id.editTextTextPassword)

            //email: prueba@prueba.com
            //contraseña: prueba123

            login.setOnClickListener{
                val email = emailEdit.text.toString().trim()
                val password = passwordEdit.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Home::class.java)
                        intent.putExtra("correo", email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        override fun onStart() {
            super.onStart()
            val currentUser = Firebase.auth.currentUser
            revisaUsuario(currentUser)
        }

        private fun revisaUsuario(usuario: FirebaseUser?) {
            if (usuario != null) {
                // Usuario ya autenticado, ir directamente a Home
                val intent = Intent(this, Home::class.java)
                intent.putExtra("correo", usuario.email)
                startActivity(intent)
                finish()
            }
        }
    }