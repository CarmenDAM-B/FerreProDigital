@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.example.ferreprodigital.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ferreprodigital.R
import com.example.ferreprodigital.data.converters.DateConverters
import com.example.ferreprodigital.data.dao.CategoryDao
import com.example.ferreprodigital.data.dao.OrderDao
import com.example.ferreprodigital.data.dao.ProductDao
import com.example.ferreprodigital.data.dao.UserDao
import com.example.ferreprodigital.data.entities.CategoryEntity
import com.example.ferreprodigital.data.entities.OrderEntity
import com.example.ferreprodigital.data.entities.ProductEntity
import com.example.ferreprodigital.data.entities.UserEntity
import com.example.ferreprodigital.data.getBricolajeProducts
import com.example.ferreprodigital.data.getFontaneriaProducts
import com.example.ferreprodigital.data.getHerramientasProducts
import com.example.ferreprodigital.data.getHogarProducts
import com.example.ferreprodigital.data.getJardinProducts
import com.example.ferreprodigital.data.getMaterialElectricoProducts
import com.example.ferreprodigital.data.getMaterialesObraProducts
import com.example.ferreprodigital.data.getRopaTrabajoProducts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * AppDatabase es la clase principal de Room que define la base de datos de la aplicación.
 *
 * Se especifican las entidades que conforman la base de datos:
 * - ProductEntity: Representa los productos.
 * - CategoryEntity: Representa las categorías de productos.
 * - UserEntity: Representa los usuarios.
 * - OrderEntity: Representa las órdenes de compra.
 *
 * La versión es 2 (lo que significa que se ha actualizado el esquema) y no se exporta el esquema.
 *
 * Además, se registra un TypeConverter para convertir tipos personalizados (en este caso, fechas)
 * usando la clase DateConverters.
 */

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        UserEntity::class,
        OrderEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {

    // Métodos abstractos que proporcionan acceso a los DAOs
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val TAG = "AppDatabase"
        private const val DATABASE_NAME = "FerrePro_Digital.db"

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                try {
                    Log.d(TAG, "Creando instancia de la base de datos...")
                    val dbFile = context.getDatabasePath(DATABASE_NAME)
                    Log.i(TAG, "Ubicación de la base de datos: ${dbFile.absolutePath}")
                    
                    dbFile.parentFile?.mkdirs()
                    
                    val callback = object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase(getDatabase(context))
                            }
                        }
                    }
                    // Construye la base de datos usando Room.databaseBuilder
                    val database = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    )
                        // Callback para la pre-población
                        .addCallback(callback)
                        .build()

                    Log.i(TAG, "Base de datos construida exitosamente")
                    INSTANCE = database
                    database
                } catch (e: Exception) {
                    Log.e(TAG, "Error al construir la base de datos: ${e.message}", e)
                    throw e
                }
            }
        }

        // Insertar datos iniciales en la base de datos.
        // Inserta categorías y productos predefinidos utilizando los DAOs.
        private suspend fun populateDatabase(db: AppDatabase) {
            try {
                Log.d(TAG, "Iniciando población de la base de datos...")

                // Obtiene el DAO de categorías y productos
                val categoryDao = db.categoryDao()
                val productDao = db.productDao()

                // Crea una lista de categorías predefinidas, insertar categorías
                val categories = listOf(
                    CategoryEntity(categoryId = 1, name = "Fontanería", imageResId = R.drawable.fontaneria),
                    CategoryEntity(categoryId = 2, name = "Herramientas", imageResId = R.drawable.herramientas),
                    CategoryEntity(categoryId = 3, name = "Hogar", imageResId = R.drawable.hogar),
                    CategoryEntity(categoryId = 4, name = "Jardín", imageResId = R.drawable.jardin),
                    CategoryEntity(categoryId = 5, name = "Material Eléctrico", imageResId = R.drawable.material_electrico),
                    CategoryEntity(categoryId = 6, name = "Materiales de Obra", imageResId = R.drawable.materiales_obra),
                    CategoryEntity(categoryId = 7, name = "Ropa de Trabajo", imageResId = R.drawable.ropa_trabajo),
                    CategoryEntity(categoryId = 8, name = "Bricolaje", imageResId = R.drawable.bricolaje)
                )

                // Inserta cada categoría en la base de datos, controlando errores
                categories.forEach { category ->
                    try {
                        categoryDao.insertCategory(category)
                        Log.d(TAG, "Categoría ${category.name} insertada")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error al insertar categoría ${category.name}: ${e.message}")
                    }
                }

                // Insertar productos por categoría (mapea)
                val productsByCategory = mapOf(
                    1 to getFontaneriaProducts(),
                    2 to getHerramientasProducts(),
                    3 to getHogarProducts(),
                    4 to getJardinProducts(),
                    5 to getMaterialElectricoProducts(),
                    6 to getMaterialesObraProducts(),
                    7 to getRopaTrabajoProducts(),
                    8 to getBricolajeProducts()
                )

                // Inserta los productos en la base de datos para cada categoría
                productsByCategory.forEach { (categoryId, products) ->
                    products.forEach { product ->
                        try {
                            // Crea un objeto ProductEntity a partir de los datos del producto
                            val productEntity = ProductEntity(
                                name = product.name,
                                price = product.price,
                                imageResId = product.imageResId,
                                categoryId = categoryId
                            )
                            productDao.insertProduct(productEntity)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error al insertar producto ${product.name}: ${e.message}")
                        }
                    }
                }

                Log.i(TAG, "Base de datos poblada exitosamente")
            } catch (e: Exception) {
                Log.e(TAG, "Error durante la población de la base de datos: ${e.message}", e)
            }
        }
    }
}
