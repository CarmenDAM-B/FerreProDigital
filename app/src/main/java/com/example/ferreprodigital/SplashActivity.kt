package com.example.ferreprodigital

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Esperar 3 segundos y luego ir a MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Siempre ir a MainActivity después del splash
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
