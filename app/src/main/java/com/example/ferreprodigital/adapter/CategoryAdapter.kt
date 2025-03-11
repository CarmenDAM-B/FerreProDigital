package com.example.ferreprodigital.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.R
import com.example.ferreprodigital.data.models.Category

/**
 * CategoryAdapter es un adaptador para un RecyclerView que muestra una lista de categorías.
 * Recibe una lista mutable de Category y una función onClick que se invoca al seleccionar una categoría.
 */

class CategoryAdapter(
    // Lista mutable de categorías para poder actualizarla dinámicamente.
    private val categoryList: MutableList<Category>,
    // Función lambda que se ejecuta cuando se hace clic en una categoría.
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // CategoryViewHolder es la clase que mantiene las referencias a las vistas de cada item.
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryImage: ImageView = view.findViewById(R.id.categoryImage)
        private val categoryName: TextView = view.findViewById(R.id.categoryName)

        // Vincula los datos de una categoría con las vistas del item.
        fun bind(category: Category, onClick: (Category) -> Unit) {
            // Se asigna la imagen y el nombre de la categoría a las vistas correspondientes.
            categoryImage.setImageResource(category.imageResId)
            categoryName.text = category.name

            // Configurar evento `onClick` correctamente
            itemView.setOnClickListener { onClick(category) }
        }
    }

    // Infla el layout de cada item del RecyclerView y crea un ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    // Vincula los datos de la lista de categorías a cada ViewHolder.
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position], onClick)
    }

    // Retorna el número total de items en la lista.
    override fun getItemCount(): Int = categoryList.size

    // Método para actualizar la lista de categorías
    // Se vacía la lista actual, se agregan los nuevos datos y se notifica los datos modificados.
    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<Category>) {
        categoryList.clear()
        categoryList.addAll(newCategories)
        notifyDataSetChanged()
    }
}
