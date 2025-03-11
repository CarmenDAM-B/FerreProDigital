package com.example.ferreprodigital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.ferreprodigital.data.database.AppDatabase
import com.example.ferreprodigital.data.repository.UserRepository
import com.example.ferreprodigital.viewmodel.UserViewModel
import com.example.ferreprodigital.viewmodel.UserViewModelFactory

class CreditActivity : BaseActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)
        setupToolbar("Contacto")

        val textViewUsuario = findViewById<TextView>(R.id.textViewUsuario)
        val buttonContactar = findViewById<ImageButton>(R.id.buttonContactar)

        // Supón que el username actual se pasa en el Intent desde MainActivity
        val currentUsername = intent.getStringExtra("username") ?: "Usuario"

        // Configurar el ViewModel para obtener la información del usuario
        val database = AppDatabase.getDatabase(this)
        val repository = UserRepository(database.userDao())
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        // Obtener el usuario por su username
        userViewModel.getUserByUsername(currentUsername)

        // Observar el usuario para actualizar la UI
        userViewModel.currentUser.observe(this) { user ->
            val usernameToShow = user?.username ?: currentUsername
            textViewUsuario.text = getString(R.string.mensaje_version, usernameToShow, 1)
        }

        // Configurar botones de contacto y redes sociales (igual que antes)
        findViewById<ImageButton>(R.id.buttonWhatsapp).setOnClickListener {
            openUrl("https://wa.me/34645811650")
        }

        buttonContactar.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:soporte@ferreprodigital.com")
                putExtra(Intent.EXTRA_SUBJECT, "Consulta de la app FerrePro Digital")
            }
            startActivity(emailIntent)
        }

        findViewById<ImageButton>(R.id.btnFacebook).setOnClickListener {
            openUrl("https://www.facebook.com/ferreprodigital")
        }

        findViewById<ImageButton>(R.id.btnInstagram).setOnClickListener {
            openUrl("https://www.instagram.com/ferreprodigital")
        }

        findViewById<ImageButton>(R.id.btnTiktok).setOnClickListener {
            openUrl("https://www.tiktok.com/@ferreprodigital")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)
        return true
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
