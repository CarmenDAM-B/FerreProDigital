package com.example.ferreprodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.R
import com.example.ferreprodigital.model.Category

class CategoryAdapter(
    private val categoryList: List<Category>,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryImage: ImageView = view.findViewById(R.id.categoryImage)
        private val categoryName: TextView = view.findViewById(R.id.categoryName)

        fun bind(category: Category, onClick: (Category) -> Unit) {
            categoryImage.setImageResource(category.imageResId)
            categoryName.text = category.name

            // Configurar evento `onClick` correctamente
            itemView.setOnClickListener { onClick(category) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position], onClick)
    }

    override fun getItemCount(): Int = categoryList.size
}
