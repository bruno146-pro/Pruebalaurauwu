package com.example.prueba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class registrarse : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var backToLoginButton: Button // Agregamos el botón para volver al login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrarse)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        registerButton = findViewById(R.id.register_button)
        backToLoginButton = findViewById(R.id.back_to_login_button) // Inicializamos el botón

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validar que los campos no estén vacíos
            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Por favor, rellene todos los datos", Toast.LENGTH_SHORT).show()
            } else {
                // Guardar en SharedPreferences
                val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                // Redirigir al menú
                val intent = Intent(this, menu::class.java)
                startActivity(intent)
                finish()
            }
        }

        backToLoginButton.setOnClickListener {
            // Redirigir al login
            val intent = Intent(this, login::class.java) // Cambia LoginActivity por el nombre de tu actividad de login
            startActivity(intent)
            finish() // Opcional, si deseas cerrar la actividad actual
        }
    }
}
