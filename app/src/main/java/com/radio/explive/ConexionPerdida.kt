package com.radio.explive

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.radio.explive.databinding.ActivityConexionPerdidaBinding

class ConexionPerdida : AppCompatActivity() {

    private lateinit var binding: ActivityConexionPerdidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conexion_perdida)




        binding = ActivityConexionPerdidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botonRefresh = findViewById<Button>(R.id.btn_refresh)
        botonRefresh.setOnClickListener{
            checkInternetConnectionRefresh()
        }



    }
    private fun checkInternetConnectionRefresh() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            // Show the error layout when there is no internet connection.
            Toast.makeText(this,"No se pudo conectar...", Toast.LENGTH_SHORT).show()
        } else {
            // Load your main content or do other actions if internet is available.

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"Recargando...", Toast.LENGTH_SHORT).show()

            finish()



        }
    }


}