package com.example.ferreprodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.R
import com.example.ferreprodigital.data.Cart
import com.example.ferreprodigital.model.Product
import java.text.NumberFormat

class ProductAdapter(
    private val productList: List<Product>,
    private val onClick: (Product) -> Unit,
    private val onQuantityChanged: (Product) -> Unit,
    private val showQuantityControls: Boolean = false // Por defecto no mostrar controles
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productImage: ImageView = view.findViewById(R.id.productImage)
        private val productName: TextView = view.findViewById(R.id.productName)
        private val productPrice: TextView = view.findViewById(R.id.productPrice)
        private val quantityControls: LinearLayout = view.findViewById(R.id.quantityControls)
        private val productQuantity: TextView = view.findViewById(R.id.productQuantity)
        private val btnIncrease: Button = view.findViewById(R.id.btnIncrease)
        private val btnDecrease: Button = view.findViewById(R.id.btnDecrease)

        fun bind(product: Product, onClick: (Product) -> Unit, onQuantityChanged: (Product) -> Unit, showControls: Boolean) {
            val format = NumberFormat.getCurrencyInstance(java.util.Locale("es", "ES"))
            productName.text = product.name
            productPrice.text = format.format(product.price)
            productImage.setImageResource(product.imageResId)
            
            quantityControls.visibility = if (showControls) View.VISIBLE else View.GONE
            
            if (showControls) {
                updateQuantityText(product)
                
                btnIncrease.setOnClickListener {
                    Cart.addItem(product)
                    val updatedProduct = Cart.getItems().find { it.name == product.name }
                    if (updatedProduct != null) {
                        updateQuantityText(updatedProduct)
                        onQuantityChanged(updatedProduct)
                    }
                }

                btnDecrease.setOnClickListener {
                    if (product.cantidad >= 1) {
                        Cart.removeItem(product)
                        val updatedProduct = Cart.getItems().find { it.name == product.name }
                        if (updatedProduct != null) {
                            updateQuantityText(updatedProduct)
                            onQuantityChanged(updatedProduct)
                        } else {
                            onQuantityChanged(product)
                        }
                    }
                }
            }

            itemView.setOnClickListener { onClick(product) }
        }

        private fun updateQuantityText(product: Product) {
            productQuantity.text = product.cantidad.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], onClick, onQuantityChanged, showQuantityControls)
    }

    override fun getItemCount(): Int = productList.size
}
