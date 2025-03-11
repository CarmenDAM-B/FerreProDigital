package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ferreprodigital.data.database.AppDatabase
import com.example.ferreprodigital.data.entities.UserEntity
import com.example.ferreprodigital.data.repository.UserRepository
import com.example.ferreprodigital.viewmodel.UserViewModel
import com.example.ferreprodigital.viewmodel.UserViewModelFactory

/**
 * RegisterActivity permite que nuevos usuarios se registren.
 * Recoge los datos de entrada (username, nombre, email, password), realiza validaciones y
 * utiliza el UserViewModel para insertar el usuario en la base de datos.
 */

class RegisterActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        try {
            // Configurar botón de retroceso
            findViewById<ImageButton>(R.id.btnBack)?.setOnClickListener {
                finish() // Cierra la actividad y vuelve a la anterior
            }

            val editTextUser = findViewById<EditText>(R.id.editTextUser)
            val editTextName = findViewById<EditText>(R.id.editTextName)
            val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
            val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
            val buttonRegister = findViewById<Button>(R.id.buttonRegister)

            // Instanciar la base de datos, el repositorio y el ViewModel para el usuario
            val database = AppDatabase.getDatabase(this)
            val repository = UserRepository(database.userDao())
            val factory = UserViewModelFactory(repository)
            userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

            // Al hacer clic en el botón de registro se validan los campos y se registra el usuario.
            buttonRegister.setOnClickListener {
                val username = editTextUser.text.toString()
                val name = editTextName.text.toString()
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()

                if (username.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    // Agregar validación de email
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(
                            this,
                            "Por favor, ingrese un email válido",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }

                    val newUser = UserEntity(
                        username = username,
                        name = name,
                        email = email,
                        password = password
                    )

                    // Registra el usuario en la base de datos
                    userViewModel.registerUser(newUser)

                    // Observa el resultado del registro
                    userViewModel.userInserted.observe(this) { success ->
                        if (success) {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                            // // Navega a MainActivity para iniciar sesión
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                        }
                    }
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
