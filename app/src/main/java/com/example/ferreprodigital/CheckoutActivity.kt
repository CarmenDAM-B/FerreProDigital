package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ferreprodigital.data.Cart
import com.example.ferreprodigital.data.database.AppDatabase
import com.example.ferreprodigital.data.entities.OrderEntity
import com.example.ferreprodigital.data.repository.OrderRepository
import com.example.ferreprodigital.viewmodel.OrderViewModel
import com.example.ferreprodigital.viewmodel.OrderViewModelFactory
import java.util.Date
import java.util.Locale

/**
 * CheckoutActivity permite al usuario confirmar la compra.
 * Se muestra el total a pagar y se solicita la dirección.
 * Al confirmar, se crea una orden que se inserta en la base de datos.
 */

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

        // Instanciar el OrderViewModel
        val database = AppDatabase.getDatabase(this)
        val orderRepository = OrderRepository(database.orderDao())
        val orderViewModelFactory = OrderViewModelFactory(orderRepository)
        val orderViewModel = ViewModelProvider(this, orderViewModelFactory)[OrderViewModel::class.java]

        buttonConfirm.setOnClickListener {
            val address = editAddress.text.toString()
            if (address.isEmpty()) {
                Toast.makeText(this, "Ingrese su dirección", Toast.LENGTH_SHORT).show()
            } else {
                // Crear una OrderEntity con el total, la dirección y la fecha actual
                val order = OrderEntity(
                    totalAmount = totalAmount,
                    address = address,
                    date = Date()
                )

                // Insertar la orden en la base de datos
                orderViewModel.insertOrder(order)

                // Observar el resultado de la inserción
                orderViewModel.orderInserted.observe(this) { success ->
                    if (success) {
                        Toast.makeText(this, "Compra realizada con éxito", Toast.LENGTH_LONG).show()
                        Cart.clearCart()

                        // Redirigir a ShopActivity y limpiar el stack
                        val intent = Intent(this, ShopActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error al realizar la compra", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)
        return true
    }
}
