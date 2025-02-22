package com.example.ferreprodigital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.TextView

class CreditActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)
        setupToolbar("Contacto")

        val textViewUsuario = findViewById<TextView>(R.id.textViewUsuario)
        val buttonContactar = findViewById<ImageButton>(R.id.buttonContactar)

        // Obtener el nombre de usuario de SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Usuario")
        
        // Versión hardcodeada por ahora (podemos cambiarla después)
        val version = 1
        textViewUsuario.text = getString(R.string.mensaje_version, username, version)

        // Configurar botón de WhatsApp
        findViewById<ImageButton>(R.id.buttonWhatsapp).setOnClickListener {
            openUrl("https://wa.me/34XXXXXXXXX") // Reemplazar con el número de WhatsApp correcto
        }

        // Configurar botón de email
        buttonContactar.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:soporte@ferreprodigital.com")
                putExtra(Intent.EXTRA_SUBJECT, "Consulta de la app FerrePro Digital")
            }
            startActivity(emailIntent)
        }

        // Configurar clicks de redes sociales
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

    // Carga el menú del toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)  // Inflar el menú
        return true
    }

    // url
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
