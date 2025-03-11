package com.example.ferreprodigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ferreprodigital.R
import com.example.ferreprodigital.data.Cart
import com.example.ferreprodigital.data.models.Product
import java.text.NumberFormat

/**
 * ProductAdapter es el adaptador para el RecyclerView que muestra la lista de productos.
 * Permite visualizar la información del producto y, opcionalmente, controlar la cantidad (si se muestran controles).
 *
 * Se utiliza DiffUtil para actualizar eficientemente la lista de productos.
 */

class ProductAdapter(
    // Lista mutable de productos que se muestra en la UI.
    private val productList: MutableList<Product>,
    // Función lambda que se ejecuta al hacer clic en un producto.
    private val onClick: (Product) -> Unit,
    // Función lambda que se ejecuta cuando cambia la cantidad del producto.
    private val onQuantityChanged: (Product) -> Unit,
    // Indica si se deben mostrar controles de cantidad.
    private val showQuantityControls: Boolean = false // Por defecto no mostrar controles
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // ProductViewHolder mantiene las referencias a las vistas del layout de cada producto.
    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val productImage: ImageView = view.findViewById(R.id.productImage)
        private val productName: TextView = view.findViewById(R.id.productName)
        private val productPrice: TextView = view.findViewById(R.id.productPrice)
        private val quantityControls: LinearLayout = view.findViewById(R.id.quantityControls)
        private val productQuantity: TextView = view.findViewById(R.id.productQuantity)
        private val btnIncrease: Button = view.findViewById(R.id.btnIncrease)
        private val btnDecrease: Button = view.findViewById(R.id.btnDecrease)

        // Vincula los datos de un producto con las vistas del item.
        // Configura la visualización y los eventos de los botones para cambiar la cantidad.
        fun bind(
            product: Product,
            onClick: (Product) -> Unit,
            onQuantityChanged: (Product) -> Unit,
            showControls: Boolean
        ) {
            // Formatea y muestra el nombre y precio del producto.
            val format = NumberFormat.getCurrencyInstance(java.util.Locale("es", "ES"))
            productName.text = product.name
            productPrice.text = format.format(product.price)
            productImage.setImageResource(product.imageResId)

            // Muestra u oculta los controles de cantidad según la variable showControls.
            quantityControls.visibility = if (showControls) View.VISIBLE else View.GONE

            // Si se muestran los controles, configura los eventos para aumentar o disminuir la cantidad.
            if (showControls) {
                updateQuantityText(product)

                // Boton para aumentar la cantidad
                btnIncrease.setOnClickListener {
                    // Agrega el producto al carrito (esto incrementa la cantidad).
                    Cart.addItem(product)
                    // Busca el producto actualizado en el carrito.
                    val updatedProduct = Cart.getItems().find { it.name == product.name }
                    if (updatedProduct != null) {
                        updateQuantityText(updatedProduct)
                        onQuantityChanged(updatedProduct)
                    }
                }

                // Boton para disminuir la cantidad
                btnDecrease.setOnClickListener {
                    if (product.quantity >= 1) {
                        // Remueve el producto o decrementa la cantidad.
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

            // Configura el evento de clic en el item para abrir detalles del producto.
            itemView.setOnClickListener { onClick(product) }
        }

        //  Actualiza el TextView que muestra la cantidad del producto.
        private fun updateQuantityText(product: Product) {
            productQuantity.text = product.quantity.toString()
        }
    }

    // Infla el layout para cada item de producto y crea un ProductViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    // Vincula los datos del producto con el ViewHolder.
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position], onClick, onQuantityChanged, showQuantityControls)
    }

    // Retorna el número de productos en la lista.
    override fun getItemCount(): Int = productList.size

    // Actualiza la lista de productos
    fun updateProducts(newProducts: List<Product>) {

        // Se utiliza DiffUtil para calcular las diferencias entre la lista antigua y la nueva.
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = productList.size
            override fun getNewListSize(): Int = newProducts.size

            // Compara si los productos son los mismos basándose en un identificador único.
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return productList[oldItemPosition].productId == newProducts[newItemPosition].productId
            }
            // Compara el contenido de los productos para ver si han cambiado.
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return productList[oldItemPosition] == newProducts[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        productList.clear()
        productList.addAll(newProducts)

        // Se notifica al adapter de los cambios de forma específica para optimizar la actualización de la UI.
        diffResult.dispatchUpdatesTo(this)
    }
}
