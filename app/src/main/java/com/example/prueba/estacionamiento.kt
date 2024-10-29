package com.example.prueba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class estacionamiento : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ver_estacionamiento)

        // Obtener SharedPreferences
        val sharedPref = getSharedPreferences("Reservas", Context.MODE_PRIVATE)

        // Lista de espacios en el estacionamiento
        val espacios = listOf(
            findViewById<TextView>(R.id.space1),
            findViewById<TextView>(R.id.space2),
            findViewById<TextView>(R.id.space3),
            findViewById<TextView>(R.id.space4),
            findViewById<TextView>(R.id.space5),
            findViewById<TextView>(R.id.space6),
            findViewById<TextView>(R.id.space7),
            findViewById<TextView>(R.id.space8)
        )

        // Contador de espacios libres
        var espaciosLibres = 0

        // Configuración para cada espacio
        espacios.forEachIndexed { index, espacio ->
            val espacioId = "space_${index + 1}"
            val estado = sharedPref.getString(espacioId, "Libre")

            espacio.text = estado // Muestra si está Libre u Ocupado

            if (estado == "Libre") {
                espaciosLibres++ // Incrementa el contador si el espacio está libre
                espacio.setOnClickListener {
                    // Redirigir a la confirmación solo si está libre
                    val intent = Intent(this, confirmacion::class.java)
                    intent.putExtra("espacioId", espacioId) // Pasar el número del espacio
                    startActivity(intent)
                }
            } else {
                // Desactivar el clic si el espacio está ocupado
                espacio.isEnabled = false
                espacio.setBackgroundResource(R.drawable.space_occupied) // Cambia el fondo para indicar que está ocupado
            }
        }

        // Actualizar el TextView de espacios disponibles
        val availableSpacesTextView = findViewById<TextView>(R.id.available_spaces)
        availableSpacesTextView.text = "Espacios disposables: $espaciosLibres"

        // Manejar el botón de volver al menú
        val buttonVolverMenu: Button = findViewById(R.id.button_volver_menu)
        buttonVolverMenu.setOnClickListener {
            val intent = Intent(this, menu::class.java) // Asegúrate de que 'MenuActivity' sea el nombre correcto
            startActivity(intent)
            finish() // Finaliza la actividad actual
        }
    }
}