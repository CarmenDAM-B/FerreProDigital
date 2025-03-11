package com.example.ferreprodigital

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.adapter.CategoryAdapter
import com.example.ferreprodigital.data.database.AppDatabase
import com.example.ferreprodigital.data.repository.CategoryRepository
import com.example.ferreprodigital.data.models.Category
import com.example.ferreprodigital.viewmodel.CategoryViewModel
import com.example.ferreprodigital.viewmodel.CategoryViewModelFactory

/**
 * ShopActivity muestra las categorías disponibles en la tienda.
 * Utiliza un RecyclerView con un GridLayoutManager para mostrar las categorías en forma de cuadrícula.
 * Al seleccionar una categoría, se redirige a ProductListActivity, pasando el nombre de la categoría.
 */

class ShopActivity : BaseActivity() {
    private lateinit var recyclerViewCategories: RecyclerView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        setupToolbar("Categorías", showBackButton = false)

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories)

        // Configurar RecyclerView con GridLayoutManager de 2 columnas
        recyclerViewCategories.layoutManager = GridLayoutManager(this, 2)

        // Inicializa el adapter con una lista vacía; luego se actualizará con los datos de la DB
        categoryAdapter = CategoryAdapter(mutableListOf()) { selectedCategory ->
            openCategoryProducts(selectedCategory)
        }

        recyclerViewCategories.adapter = categoryAdapter

        // Instanciar la base de datos y configurar el ViewModel para categorías
        val database = AppDatabase.getDatabase(this)
        val categoryDao = database.categoryDao()
        val repository = CategoryRepository(categoryDao)
        val viewModelFactory = CategoryViewModelFactory(repository)

        // Observa la lista de categorías y actualiza el adapter.
        categoryViewModel = ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]

        // Observar los cambios en la lista de categorías
        categoryViewModel.categoryList.observe(this) { categoryEntities ->
            // Mapear CategoryEntity a Category (modelo de UI)
            val categories = categoryEntities.map { entity ->
                Category(
                    name = entity.name,
                    imageResId = entity.imageResId
                )
            }
            // Actualiza el adapter
            categoryAdapter.updateCategories(categories)
        }

        // Cargar las categorías desde la base de datos
        categoryViewModel.loadCategories()
    }

    /**
     * Al seleccionar una categoría se abre ProductListActivity,
     * pasando el nombre de la categoría como extra.
     */

    private fun openCategoryProducts(category: Category) {
        val intent = Intent(this, ProductListActivity::class.java)

        // Se pasa el nombre de la categoría (en ProductListActivity se mapea a un categoryId)
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
