package com.example.ferreprodigital

import android.content.Intent
import android.content.SharedPreferences
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ferreprodigital.data.Cart
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    // Configura la barra de herramientas con título y botón de retroceso
    protected fun setupToolbar(title: String, showBackButton: Boolean = true) {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(showBackButton)
            setDisplayShowHomeEnabled(showBackButton)
            setTitle(title)
        }
    }

    // Maneja la selección de opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Volver a la actividad anterior
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_home -> {
                // Redirigir a ShopActivity si no estamos ya en ella
                if (this::class.java.simpleName != "ShopActivity") {
                    val intent = Intent(this, ShopActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                true
            }
            R.id.action_logout -> {
                // Cerrar sesión
                logout()
                true
            }
            R.id.action_cart -> {
                // Redirigir a CartActivity
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            R.id.action_contact -> {
                // Redirigir a CreditActivity
                startActivity(Intent(this, CreditActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Cierra la sesión del usuario
    private fun logout() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            clear()
            remove("isLoggedIn")
            remove("username")
            remove("password")
            apply()
        }
        Cart.clearCart() // Limpiar el carrito de compras
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        // Redirigir a MainActivity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
