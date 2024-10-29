package com.example.prueba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

@Suppress("NAME_SHADOWING", "DEPRECATION")
class perfil : AppCompatActivity() {

    private lateinit var usernameTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var showPasswordButton: Button
    private lateinit var saveDataButton: Button
    private lateinit var deleteUsernameButton: ImageView
    private lateinit var deletePasswordButton: ImageView
    private lateinit var editUsernameButton: ImageView
    private lateinit var editPasswordButton: ImageView
    private lateinit var backToMenuButton: Button
    private var isPasswordVisible = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil)

        usernameTextView = findViewById(R.id.username)
        passwordTextView = findViewById(R.id.password)
        showPasswordButton = findViewById(R.id.showPasswordButton)
        saveDataButton = findViewById(R.id.saveDataButton)
        deleteUsernameButton = findViewById(R.id.deleteUsernameButton)
        deletePasswordButton = findViewById(R.id.deletePasswordButton)
        editUsernameButton = findViewById(R.id.editUsernameButton)
        editPasswordButton = findViewById(R.id.editPasswordButton)
        backToMenuButton = findViewById(R.id.backToMenuButton)

        // Obtener datos de SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")

        // Establecer los textos en los TextViews
        usernameTextView.text = username
        passwordTextView.text = password

        // Mostrar/ocultar la contraseña al hacer clic en el botón
        showPasswordButton.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordTextView.text = password // Mostrar contraseña
                showPasswordButton.text = "Ocultar Contraseña"
            } else {
                passwordTextView.text = "********" // Ocultar contraseña
                showPasswordButton.text = "Mostrar Contraseña"
            }
        }

        // Manejar clic en el botón de borrar nombre de usuario
        deleteUsernameButton.setOnClickListener {
            usernameTextView.text = "" // Borrar el texto del nombre de usuario
            Toast.makeText(this, "Nombre de usuario borrado.", Toast.LENGTH_SHORT).show()
            saveToSharedPreferences("username", "") // Borrar el nombre de usuario
            checkInput() // Verificar si los campos están vacíos
        }

        // Manejar clic en el botón de borrar contraseña
        deletePasswordButton.setOnClickListener {
            passwordTextView.text = "" // Borrar el texto de la contraseña
            Toast.makeText(this, "Contraseña borrada.", Toast.LENGTH_SHORT).show()
            saveToSharedPreferences("password", "") // Borrar la contraseña
            checkInput() // Verificar si los campos están vacíos
        }

        // Manejar clic en el botón de editar nombre de usuario
        editUsernameButton.setOnClickListener {
            showEditDialog("Editar Nombre de Usuario", usernameTextView.text.toString()) { newUsername ->
                if (isUsernameAvailable(newUsername)) {
                    usernameTextView.text = newUsername
                    saveToSharedPreferences("username", newUsername)
                    checkInput() // Verificar si los campos están vacíos
                } else {
                    Toast.makeText(this, "El nombre de usuario ya está en uso.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Manejar clic en el botón de editar contraseña
        editPasswordButton.setOnClickListener {
            showEditDialog("Editar Contraseña", passwordTextView.text.toString()) { newPassword ->
                passwordTextView.text = newPassword
                saveToSharedPreferences("password", newPassword)
                checkInput() // Verificar si los campos están vacíos
            }
        }

        // Manejar clic en el botón de guardar datos
        saveDataButton.setOnClickListener {
            val username = usernameTextView.text.toString()
            val password = passwordTextView.text.toString()
            saveToSharedPreferences("username", username)
            saveToSharedPreferences("password", password)
            Toast.makeText(this, "Datos guardados exitosamente.", Toast.LENGTH_SHORT).show()
        }

        // Manejar clic en el botón de volver al menú
        backToMenuButton.setOnClickListener {
            if (usernameTextView.text.isEmpty() || passwordTextView.text.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un nombre y una contraseña antes de salir.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, menu::class.java)
                startActivity(intent)
                finish() // Opcional: Cerrar esta actividad
            }
        }
    }

    private fun isUsernameAvailable(newUsername: String): Boolean {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val existingUsername = sharedPref.getString("username", "")
        return existingUsername != newUsername
    }

    private fun saveToSharedPreferences(key: String, value: String) {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    private fun showEditDialog(title: String, currentText: String, onConfirm: (String) -> Unit) {
        val editText = EditText(this)
        editText.setText(currentText)

        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(editText)
            .setPositiveButton("Aceptar") { _, _ ->
                onConfirm(editText.text.toString())
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun checkInput() {
        backToMenuButton.isEnabled = !(usernameTextView.text.isEmpty() || passwordTextView.text.isEmpty())
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        // Verificar si los campos están vacíos
        if (usernameTextView.text.isEmpty() || passwordTextView.text.isEmpty()) {
            // Mostrar un diálogo de confirmación si hay datos no guardados
            AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("Tienes datos no guardados. ¿Estás seguro de que quieres salir?")
                .setPositiveButton("Salir") { _, _ ->
                    super.onBackPressed() // Permitir salir
                }
                .setNegativeButton("Cancelar", null) // Solo cierra el diálogo
                .show()
        } else {
            // Si los campos no están vacíos, permitir salir directamente
            super.onBackPressed()
        }
    }
}
