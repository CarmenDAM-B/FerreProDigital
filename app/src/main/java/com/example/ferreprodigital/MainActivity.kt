package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Limpiar cualquier sesión anterior al iniciar
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            clear()
            remove("isLoggedIn")
            remove("username")
            remove("password")
            apply()
        }

        // Establecer credenciales predefinidas
        with(sharedPreferences.edit()) {
            putString("username", "admin")
            putString("password", "1234")
            apply()
        }

        setupLoginUI()  // Mostrar la interfaz de login
    }

    private fun setupLoginUI() {
        val editTextUser = findViewById<EditText>(R.id.editTextUser)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            val username = editTextUser.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (validateCredentials(username, password)) {
                    // Guardar el estado de sesión
                    with(getSharedPreferences("UserSession", MODE_PRIVATE).edit()) {
                        putString("username", username)
                        putBoolean("isLoggedIn", true)
                        apply()
                    }

                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    login(username, password)
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Usuario y contraseña requeridos", Toast.LENGTH_SHORT).show()
            }
        }

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateCredentials(username: String, password: String): Boolean {
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val registeredUsername = sharedPreferences.getString("username", "")
        val registeredPassword = sharedPreferences.getString("password", "")
        
        // Agregar log para debug
        println("Stored username: $registeredUsername, password: $registeredPassword")
        println("Input username: $username, password: $password")
        
        return username == registeredUsername && password == registeredPassword
    }

    private fun login(username: String, password: String) {
        val intent = Intent(this, ShopActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }
}
