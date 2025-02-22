package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.adapter.ProductAdapter
import com.example.ferreprodigital.data.Cart
import android.view.Menu
import android.util.Log

class CartActivity : BaseActivity() {
    private lateinit var recyclerViewCart: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var textTotal: TextView
    private lateinit var btnCheckout: Button

    // Método onCreate, donde se inicializan las vistas y se configura la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setupToolbar("Carrito")

        try {
            // Inicialización de las vistas
            recyclerViewCart = findViewById(R.id.recyclerViewCart)
            textTotal = findViewById(R.id.textTotal)
            btnCheckout = findViewById(R.id.btnCheckout)

            // Configuración del RecyclerView
            recyclerViewCart.layoutManager = LinearLayoutManager(this)
            productAdapter = ProductAdapter(
                productList = Cart.getItems(),  // Obtener productos del carrito
                onClick = { product ->
                    Toast.makeText(this, "${product.name} seleccionado", Toast.LENGTH_SHORT).show()
                },
                onQuantityChanged = {  // Actualizar el total y el carrito cuando la cantidad cambia
                    updateTotal()
                    updateCart()
                },
                showQuantityControls = true
            )
            recyclerViewCart.adapter = productAdapter

            // Configuración del botón de checkout
            btnCheckout.setOnClickListener {
                Log.d("CartActivity", "Botón checkout presionado")
                if (Cart.getItems().isNotEmpty()) {
                    // Si el carrito tiene productos, proceder con el pago
                    Log.d("CartActivity", "Carrito no está vacío, total: ${Cart.getTotal()}")
                    try {
                        val checkoutIntent = Intent(this@CartActivity, CheckoutActivity::class.java)
                        checkoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        checkoutIntent.putExtra("TOTAL_AMOUNT", Cart.getTotal())  // Pasar el total al checkout
                        startActivity(checkoutIntent)
                        Log.d("CartActivity", "Intent de CheckoutActivity iniciado")
                    } catch (e: Exception) {
                        Log.e("CartActivity", "Error al iniciar CheckoutActivity", e)
                        Toast.makeText(this, "Error al procesar el pago", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Si el carrito está vacío, mostrar un mensaje
                    Log.d("CartActivity", "Carrito está vacío")
                    Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                }
            }

            updateTotal()  // Actualizar el total al cargar la actividad
        } catch (e: Exception) {
            Log.e("CartActivity", "Error en onCreate", e)
            Toast.makeText(this, "Error al inicializar la vista: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    // Actualizar el total en el TextView correspondiente
    @SuppressLint("DefaultLocale")
    private fun updateTotal() {
        val total = Cart.getTotal()
        textTotal.text = getString(R.string.total_a_pagar, total)  // Mostrar el total formateado
    }

    // Notificar al adaptador que los datos han cambiado
    @SuppressLint("NotifyDataSetChanged")
    private fun updateCart() {
        productAdapter.notifyDataSetChanged()  // Actualizar los productos en el RecyclerView
    }

    // Carga el menú del toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)  // Inflar el menú
        return true
    }
}

