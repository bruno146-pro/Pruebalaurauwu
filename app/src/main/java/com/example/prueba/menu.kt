package com.example.prueba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu) // Asegúrate de que esto coincida con tu archivo XML

        val verPerfilButton: Button = findViewById(R.id.ver_perfil_button)
        val verEstacionamientosButton: Button = findViewById(R.id.ver_estacionamientos_button)
        val salirButton: Button = findViewById(R.id.salir_button) // Botón para salir
        val reservasButton: Button = findViewById(R.id.reservas_button) // Nuevo botón para reservas

        verPerfilButton.setOnClickListener {
            val intent = Intent(this, perfil::class.java)
            startActivity(intent)
        }

        verEstacionamientosButton.setOnClickListener {
            val intent = Intent(this, estacionamiento::class.java)
            startActivity(intent)
        }

        // Manejar clic en el botón de salir
        salirButton.setOnClickListener {
            val intent = Intent(this, login::class.java) // Cambia a la actividad de login
            startActivity(intent)
            finish() // Opcional: Cerrar esta actividad
        }

        // Manejar clic en el botón de reservas
        reservasButton.setOnClickListener {
            val intent = Intent(this, reservas::class.java) // Cambia a la actividad de reservas
            startActivity(intent)
        }
    }
}
