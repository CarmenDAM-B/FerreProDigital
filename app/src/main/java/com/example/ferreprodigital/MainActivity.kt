package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ferreprodigital.data.database.AppDatabase
import com.example.ferreprodigital.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MainActivity es la pantalla de login.
 * Permite que el usuario inicie sesión verificando sus credenciales contra la base de datos.
 */

@SuppressLint("MissingInflatedId")
class MainActivity : AppCompatActivity() {

    private lateinit var editTextUser: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        editTextUser = findViewById(R.id.editTextUser)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Configurar el botón de Login
        buttonLogin.setOnClickListener {
            val username = editTextUser.text.toString()
            val password = editTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Consultar el usuario en la base de datos
                val database = AppDatabase.getDatabase(this)
                val repository = UserRepository(database.userDao())
                lifecycleScope.launch {
                    val user = repository.getUserByUsername(username)
                    withContext(Dispatchers.Main) {
                        if (user != null && user.password == password) {
                            Toast.makeText(this@MainActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            login(username, password)
                        } else {
                            Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Usuario y contraseña requeridos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el botón de Registro
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // Si el login es exitoso, se inicia ShopActivity pasando el username.
    private fun login(username: String, password: String) {
        val intent = Intent(this, ShopActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }
}
