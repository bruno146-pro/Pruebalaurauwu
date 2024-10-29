package com.example.prueba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class confirmacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.confirmacion)

        val espacioId = intent.getStringExtra("espacioId") // Obtener el ID del espacio
        val nombreEditText = findViewById<EditText>(R.id.nombreEditText) // Campo para ingresar nombre
        val confirmarButton = findViewById<Button>(R.id.confirmarButton)

        confirmarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString() // Obtener el nombre ingresado

            // Guardar en SharedPreferences
            val sharedPref = getSharedPreferences("Reservas", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            // Cambiar el estado del espacio a "Ocupado" y guardar el nombre
            editor.putString(espacioId, "Ocupado")
            editor.putString("${espacioId}_nombre", nombre)
            editor.apply() // Asegúrate de aplicar los cambios

            // Redirigir al menú después de confirmar
            val intent = Intent(this, menu::class.java) // Cambia MenuActivity por el nombre de tu clase de actividad para el menú
            startActivity(intent)
            finish() // Termina la actividad actual para que no se pueda volver a ella con el botón atrás
        }
    }
}
