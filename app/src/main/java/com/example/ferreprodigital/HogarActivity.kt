package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.adapter.ProductAdapter
import com.example.ferreprodigital.data.getHogarProducts
import com.example.ferreprodigital.model.Product

class HogarActivity : AppCompatActivity() {
    private lateinit var recyclerViewProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts) // 🔹 Referencia correcta del RecyclerView

        setupHogarRecyclerView()
    }

    private fun setupHogarRecyclerView() {
        val products = getHogarProducts()
        val adapter = ProductAdapter(
            products,
            { selectedProduct ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT_NAME", selectedProduct.name)
            intent.putExtra("PRODUCT_PRICE", selectedProduct.price)
            intent.putExtra("PRODUCT_IMAGE", selectedProduct.imageResId)
            startActivity(intent)
        },

        { _: Product -> // onQuantityChanged lambda
                // Aquí puedes manejar cualquier lógica adicional cuando la cantidad cambia
        }
    )

    recyclerViewProducts.adapter = adapter
    recyclerViewProducts.layoutManager = LinearLayoutManager(this)
    }
}
