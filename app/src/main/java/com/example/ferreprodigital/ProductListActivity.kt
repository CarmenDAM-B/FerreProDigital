package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.adapter.ProductAdapter
import com.example.ferreprodigital.data.getFontaneriaProducts
import com.example.ferreprodigital.data.getHogarProducts
import com.example.ferreprodigital.data.getJardinProducts
import com.example.ferreprodigital.data.getMaterialElectricoProducts
import com.example.ferreprodigital.data.getMaterialesObraProducts
import com.example.ferreprodigital.data.getRopaTrabajoProducts
import com.example.ferreprodigital.data.getHerramientasProducts
import com.example.ferreprodigital.data.getBricolajeProducts
import com.example.ferreprodigital.model.Product

class ProductListActivity : BaseActivity() {
    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setupToolbar("Lista de Productos")

        // Inicializar vistas usando los IDs correctos del layout
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts)

        // Configurar RecyclerView
        recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        // Inicializar productAdapter
        productAdapter = ProductAdapter(
            productList = productList,
            onClick = { selectedProduct -> // onClick lambda
                openProductDetail(selectedProduct)
            },
            onQuantityChanged = { _ -> // Usando _ para parámetro no utilizado
                // Manejar cambios en la cantidad si es necesario
            }
        )
        recyclerViewProducts.adapter = productAdapter

        // Obtener la categoría seleccionada
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Categoría Desconocida"
        println("Categoría seleccionada: $categoryName")

        // Llamar a la función para cargar productos
        loadProducts(categoryName)
    }

    private fun openProductDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("PRODUCT_NAME", product.name)
            putExtra("PRODUCT_PRICE", product.price)
            putExtra("PRODUCT_IMAGE", product.imageResId)
        }
        startActivity(intent)
    }

    // Cargar productos de la categoría seleccionada
    private fun loadProducts(category: String) {
        productList.clear() // vacia la lista de productos

        val products = when (category) {
            "Fontanería" -> getFontaneriaProducts()
            "Herramientas" -> getHerramientasProducts()
            "Materiales de Obra" -> getMaterialesObraProducts()
            "Ropa de Trabajo" -> getRopaTrabajoProducts()
            "Material Eléctrico" -> getMaterialElectricoProducts()
            "Hogar" -> getHogarProducts()
            "Jardín" -> getJardinProducts()
            "Bricolaje" -> getBricolajeProducts()
            else -> emptyList()
        }

        productList.addAll(products)

        // Notificar cambio en los datos
        productAdapter.notifyItemRangeInserted(0, productList.size) // 🔥 Más eficiente
    }

    // Carga el menú del toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)
        return true
    }
}

