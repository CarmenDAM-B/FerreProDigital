package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import java.util.Locale
import java.text.NumberFormat
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ferreprodigital.data.Cart
import com.example.ferreprodigital.data.models.Product
import android.util.Log

class ProductDetailActivity : BaseActivity() {
    // Método llamado cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setupToolbar("Detalle del Producto")

        try {
            // Obtener referencias a los elementos de la UI
            val productName = findViewById<TextView>(R.id.productName)
            val productPrice = findViewById<TextView>(R.id.productPrice)
            val productImage = findViewById<ImageView>(R.id.productImage)
            val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)

            // Obtener datos del producto seleccionado
            val name = intent.getStringExtra("PRODUCT_NAME") ?: "Sin nombre"
            val price = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
            val imageResId = intent.getIntExtra("PRODUCT_IMAGE", 0)

            Log.d("ProductDetail", "Datos recibidos - nombre: $name, precio: $price, imagen: $imageResId")

            // Mostrar datos en la UI
            productName.text = name
            val format = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
            productPrice.text = format.format(price)
            productImage.setImageResource(imageResId)

            // Agregar al carrito
            btnAddToCart.setOnClickListener {
                try {
                    Log.d("ProductDetail", "Botón agregar al carrito presionado")
                    
                    val product = Product(
                        productId = 0,
                        name = name,
                        price = price,
                        imageResId = imageResId,
                        quantity = 1
                    )
                    Log.d("ProductDetail", "Producto creado: $product")

                    Cart.addItem(product)
                    Log.d("ProductDetail", "Producto agregado al carrito")
                    
                    Toast.makeText(this, "$name añadido al carrito", Toast.LENGTH_SHORT).show()
                    
                    // Modificar la navegación a CartActivity
                    try {
                        val intent = Intent(this@ProductDetailActivity, CartActivity::class.java)
                        // Agregar flags para manejar la navegación
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        Log.d("ProductDetail", "Navegando a CartActivity")
                    } catch (e: Exception) {
                        Log.e("ProductDetail", "Error al navegar a CartActivity", e)
                        Toast.makeText(
                            this,
                            "Error al abrir el carrito: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    
                } catch (e: Exception) {
                    Log.e("ProductDetail", "Error general en onClick", e)
                    Toast.makeText(
                        this,
                        "Error al agregar al carrito: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Log.e("ProductDetail", "Error en onCreate", e)
            Toast.makeText(this, "Error al cargar el producto", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    // Carga el menú del toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)
        return true
    }
}
