package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ferreprodigital.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * SplashActivity es la pantalla de inicio (splash screen) que se muestra al arrancar la app.
 * Inicializa la base de datos (para forzar la creación y pre-población) y, tras unos segundos,
 * redirige a MainActivity.
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SplashActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "SplashActivity onCreate iniciado")
        setContentView(R.layout.activity_splash)

        // Inicializar la base de datos en un coroutine
        Log.w(TAG, "Iniciando coroutine para crear base de datos")
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                Log.i(TAG, "Iniciando verificación de la base de datos")
                val database = AppDatabase.getDatabase(this@SplashActivity)
                
                // Consulta simple para forzar la creación de la base.
                val categories = database.categoryDao().getAllCategories()
                Log.i(TAG, "Categorías encontradas: ${categories.size}")
                
                categories.forEach { category ->
                    Log.d(TAG, "Categoría: ${category.name}")
                }

                // Conmutar al hilo principal y esperar 3 segundos para la transición.
                withContext(Dispatchers.Main) {
                    Log.i(TAG, "Preparando transición a MainActivity")
                    Handler(Looper.getMainLooper()).postDelayed({
                        Log.i(TAG, "Iniciando MainActivity")
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }, 3000)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al acceder a la base de datos: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    // Si ocurre un error, igualmente se procede a ir a MainActivity
                    Log.w(TAG, "Iniciando MainActivity después de error")
                    Handler(Looper.getMainLooper()).postDelayed({
                        Log.w(TAG, "Iniciando MainActivity después de error")
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }, 3000)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "SplashActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "SplashActivity onResume")
    }
}
