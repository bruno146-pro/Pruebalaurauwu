package com.example.prueba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class reservas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservas) // Asegúrate de que esto coincida con tu archivo XML

        // Obtener SharedPreferences
        val sharedPref = getSharedPreferences("Reservas", Context.MODE_PRIVATE)

        // Recuperar los nombres de los espacios reservados
        val reservedSpaces = mutableListOf<String>()
        for (i in 1..8) { // Suponiendo que tienes 8 espacios
            val espacioId = "space_$i"
            val estado = sharedPref.getString(espacioId, "Libre")
            if (estado == "Ocupado") {
                // Obtener el nombre asociado al espacio
                val nombre = sharedPref.getString("${espacioId}_nombre", "Desconocido") ?: "Desconocido"
                reservedSpaces.add("Espacio $i: $nombre")
            }
        }

        // Mostrar la lista de espacios reservados en un TextView
        val reservedSpacesTextView = findViewById<TextView>(R.id.reserved_spaces_text_view)
        reservedSpacesTextView.text = if (reservedSpaces.isNotEmpty()) {
            reservedSpaces.joinToString("\n")
        } else {
            "No hay espacios reservados."
        }

        // Manejar el botón de volver al menú
        val volverButton: Button = findViewById(R.id.volver_button) // Asegúrate de que el ID sea correcto
        volverButton.setOnClickListener {
            val intent = Intent(this, menu::class.java) // Cambia 'menu' por el nombre de tu actividad de menú
            startActivity(intent)
            finish() // Termina esta actividad
        }
    }
}
