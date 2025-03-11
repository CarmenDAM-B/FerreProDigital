package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.adapter.ProductAdapter
import com.example.ferreprodigital.data.database.AppDatabase
import com.example.ferreprodigital.data.repository.ProductRepository
import com.example.ferreprodigital.data.models.Product
import com.example.ferreprodigital.viewmodel.ProductViewModel
import com.example.ferreprodigital.viewmodel.ProductViewModelFactory

class ProductListActivity : BaseActivity() {
    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setupToolbar("Lista de Productos")

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts)
        recyclerViewProducts.layoutManager = LinearLayoutManager(this)

        // Inicializa el adapter con una lista vacía
        productAdapter = ProductAdapter(mutableListOf(), onClick = { selectedProduct ->
            openProductDetail(selectedProduct)
        }, onQuantityChanged = { _ ->

        })
        recyclerViewProducts.adapter = productAdapter

        // Instanciar la base de datos, el repository y el ViewModel
        val database = AppDatabase.getDatabase(this)
        val repository = ProductRepository(
            productDao = database.productDao(),
            categoryDao = database.categoryDao()
        )
        val viewModelFactory = ProductViewModelFactory(repository)
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        // Obtener la categoría del Intent y mapearla a un categoryId
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""
        val categoryId = when (categoryName) {
            "Fontanería" -> 1
            "Herramientas" -> 2
            "Hogar" -> 3
            "Jardín" -> 4
            "Material Eléctrico" -> 5
            "Materiales de Obra" -> 6
            "Ropa de Trabajo" -> 7
            "Bricolaje" -> 8
            else -> 0
        }

        // Si categoryId es 0, cargar todos los productos; si no, filtrar por categoría
        if (categoryId == 0) {
            productViewModel.loadProducts()
        } else {
            productViewModel.loadProductsByCategory(categoryId)
        }

        // Observar el LiveData para actualizar la UI
        productViewModel.productList.observe(this) { productEntities ->
            // Mapea ProductEntity a Product (modelo para la UI)
            val products = productEntities.map { entity ->
                Product(
                    productId = entity.productId,
                    name = entity.name,
                    price = entity.price,
                    imageResId = entity.imageResId,
                    quantity = 1
                )
            }
            productAdapter.updateProducts(products)
        }
    }

    private fun openProductDetail(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra("PRODUCT_NAME", product.name)
            putExtra("PRODUCT_PRICE", product.price)
            putExtra("PRODUCT_IMAGE", product.imageResId)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)
        return true
    }
}
