package com.example.ferreprodigital

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        try {
            // Configurar botón de retroceso
            findViewById<ImageButton>(R.id.btnBack)?.setOnClickListener {
                finish() // Cierra la actividad y vuelve a la anterior
            }

            // Inicializar SharedPreferences para guardar los datos
            sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)

            val editTextUser = findViewById<EditText>(R.id.editTextUser)
            val editTextName = findViewById<EditText>(R.id.editTextName)
            val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
            val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
            val buttonRegister = findViewById<Button>(R.id.buttonRegister)

            buttonRegister.setOnClickListener {
                val username = editTextUser.text.toString()
                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

                if (username.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    // Agregar validación de email
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(this, "Por favor, ingrese un email válido", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    // Guardar los datos en SharedPreferences
                    with(sharedPreferences.edit()) {
                        putString("username", username)
                        putString("name", name)
                        putString("email", email)
                        putString("password", password)
                        putBoolean("isRegistered", true)
                        apply()
                    }

                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                    // Ir a la pantalla de Login (MainActivity)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("RegisterActivity", "Error al inicializar la vista", e)
            Toast.makeText(this, "Error al inicializar la vista", Toast.LENGTH_SHORT).show()
        }
    }
}
