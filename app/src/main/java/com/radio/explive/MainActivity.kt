package com.radio.explive

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.radio.explive.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dialogo
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar
    private val canalNombre = "e_xplosion"
    private val canalID = "ID"
    private val notificacionID = 0


    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //variable WebViewer
        //webViewSetup()
        val webview = findViewById<WebView>(R.id.webview)
        webview.settings.javaScriptEnabled = true
        binding.webview.reload()
        



        //toolbar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_close,
            R.string.navigation_drawer_open
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        //cambiar entre frames con el menu lateral

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {


                R.id.inicio -> {
                    // ir a Inicio
                    Toast.makeText(this,"Ya estás en el inicio.", Toast.LENGTH_SHORT).show()
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.rec_reproductor -> {
                    // ir a Inicio
                    checkInternetConnectionRefresh()
                    drawer.closeDrawer(GravityCompat.START)
                    webview.reload()
                    true
                }

                R.id.instagram -> {
                    // ir a Facebook
                    val websiteUrl = "https://www.instagram.com/e_xplosion.live/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                    startActivity(intent)
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.facebook -> {
                    // ir a Instagram
                    val websiteUrl = "https://www.facebook.com/explosionradiolive/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                    startActivity(intent)
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.programacion -> {
                    // ir a Programacion
                    startActivity(Intent(this, prog2::class.java))
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.version -> {
                    // ir a About us...
                    val dialogFragment = dialogo()
                    dialogFragment.show(supportFragmentManager, "dialogVersion")
                    true
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }

                R.id.aboutus -> {
                    // ir a About us...
                    startActivity(Intent(this, terminosCondiciones::class.java))
                    true
                    drawer.closeDrawer(GravityCompat.START)
                    true
                }


                R.id.salir -> {
                    // ir a About us...
                    exitApp()
                    closeWebView()

                    true
                }



                else -> false
            }.also {
                drawerLayout.closeDrawers()
            }
        }

        //finaliza el bloque de frames


        checkInternetConnection()



    }

    override fun onStop() {
        super.onStop()
        crearCanalNotificacion()
        crearlNotificacion()
    }

    override fun onDestroy() {
        super.onDestroy()
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(notificacionID)

    }




    fun exitApp() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity()
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.cancel(notificacionID)

        } else {
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.cancel(notificacionID)
            finish()
        }
    }

    private fun closeWebView() {
        // Detener la carga y destruir el WebView
        val webview = findViewById<WebView>(R.id.webview)
        webview.settings.javaScriptEnabled = true
        webview.stopLoading()
        webview.destroy()

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){



        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){return true}

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }





    //Fun de WebViewer
    // private fun webViewSetup() {
    //   val webView = findViewById<WebView>(R.id.webview)
    //    webView.webViewClient= WebViewClient()
    //    webView.loadUrl("https://subterranean-audito.000webhostapp.com/Player/")
    //    webView.settings.javaScriptEnabled=true
    //    webView.settings.setSupportZoom(true)

    //  }

    private fun checkInternetConnection() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            // Show the error layout when there is no internet connection.
            Toast.makeText(this,"Error al conectar.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ConexionPerdida::class.java)
            startActivity(intent)
            finish()
        } else {
            // Load your main content or do other actions if internet is available.
            val webView = findViewById<WebView>(R.id.webview)
            webView.webViewClient= WebViewClient()
            webView.loadUrl("https://subterranean-audito.000webhostapp.com/Player/")
            webView.settings.javaScriptEnabled=true
            webView.settings.setSupportZoom(true)

        }
    }

    private fun checkInternetConnectionRefresh() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            // Show the error layout when there is no internet connection.
            Toast.makeText(this,"Conexión perdida.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ConexionPerdida::class.java)
            startActivity(intent)
            finish()
        } else {
            // Load your main content or do other actions if internet is available.
            val webView = findViewById<WebView>(R.id.webview)
            webView.webViewClient= WebViewClient()
            webView.reload()
            Toast.makeText(this,"Recargando...", Toast.LENGTH_SHORT).show()

        }
    }


    @SuppressLint("ResourceAsColor")
    private fun crearlNotificacion() {

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)


        val notificacion = NotificationCompat.Builder(this,canalID).also {
            it.setContentTitle("Notificación")
            it.setContentText("Toca aquí cuando quieras volver a la aplicación.")
            it.setSmallIcon(R.drawable.icon_wht)
            it.setColor(R.color.black)
            it.setOngoing(true)
            it.priority=NotificationCompat.PRIORITY_LOW
            it.setContentIntent(pendingIntent)
            it.setAutoCancel(true)
            it.setColor(ContextCompat.getColor(this, R.color.black))



        }.build()

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificacionID, notificacion)
    }

    private fun crearCanalNotificacion(){

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            val canalImportancia = NotificationManager.IMPORTANCE_LOW
            val canal = NotificationChannel(canalID, canalNombre, canalImportancia)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(canal)


        }


    }



}

