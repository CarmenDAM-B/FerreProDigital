package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ferreprodigital.data.Cart
import java.util.Locale

class CheckoutActivity : BaseActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setupToolbar("Pago")

        val totalAmount = intent.getDoubleExtra("TOTAL_AMOUNT", 0.0)
        val textTotal = findViewById<TextView>(R.id.textTotal)
        val editAddress = findViewById<EditText>(R.id.editAddress)
        val buttonConfirm = findViewById<Button>(R.id.buttonConfirm)

        textTotal.text = String.format(Locale("es", "ES"), "Total a pagar: %.2f€", totalAmount)

        buttonConfirm.setOnClickListener {
            val address = editAddress.text.toString()
            if (address.isEmpty()) {
                Toast.makeText(this, "Ingrese su dirección", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_LONG).show()
                Cart.clearCart()

                // Redirigir a ShopActivity y limpiar el stack
                val intent = Intent(this, ShopActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    // Crear el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)  // Inflar el menú
        return true
    }
}

