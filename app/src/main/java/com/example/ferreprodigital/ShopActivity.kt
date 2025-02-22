package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.adapter.CategoryAdapter
import com.example.ferreprodigital.model.Category

class ShopActivity : BaseActivity() {
    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        setupToolbar("Categorías", showBackButton = false)

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories)

        // Configurar RecyclerView con GridLayoutManager de 2 columnas
        recyclerViewCategories.layoutManager = GridLayoutManager(this, 2)

        val categories = listOf(
            Category("Fontanería", R.drawable.fontaneria),
            Category("Herramientas", R.drawable.herramientas),
            Category("Materiales de Obra", R.drawable.materiales_obra),
            Category("Ropa de Trabajo", R.drawable.ropa_trabajo),
            Category("Material Eléctrico", R.drawable.material_electrico),
            Category("Hogar", R.drawable.hogar),
            Category("Jardín", R.drawable.jardin),
            Category("Bricolaje", R.drawable.bricolaje)
        )

        categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            openCategoryProducts(selectedCategory)
        }

        recyclerViewCategories.adapter = categoryAdapter
    }

    private fun openCategoryProducts(category: Category) {
        val intent = Intent(this, ProductListActivity::class.java)
        intent.putExtra("CATEGORY_NAME", category.name)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shop, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
