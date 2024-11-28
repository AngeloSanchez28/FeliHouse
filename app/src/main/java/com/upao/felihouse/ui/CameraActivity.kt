package com.upao.felihouse.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.airbnb.lottie.LottieAnimationView
import com.upao.felihouse.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class CameraActivity : BaseActivity() {
    private val flashOnUrl = "https://d9c8-190-108-95-235.ngrok-free.app/flash/on" // Reemplaza <IP_DE_TU_ESP32> con la IP de tu ESP32-CAM
    private val flashOffUrl = "https://d9c8-190-108-95-235.ngrok-free.app/flash/off"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_camera, findViewById(R.id.container))

        // Cambiar el color del status bar dinámicamente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.melon)
        }

        val lottieSwitch = findViewById<LottieAnimationView>(R.id.lottie_switch_master_room)

        var isSwitchOn = false

        lottieSwitch.setOnClickListener {
            if (isSwitchOn) {
                lottieSwitch.speed = -1f // Reproduce la animación en reversa
                lottieSwitch.playAnimation()
                isSwitchOn = false

                // Llama al endpoint para apagar el flash
                toggleFlash(flashOffUrl)
            } else {
                lottieSwitch.speed = 1f // Reproduce la animación normalmente
                lottieSwitch.playAnimation()
                isSwitchOn = true

                // Llama al endpoint para encender el flash
                toggleFlash(flashOnUrl)
            }
        }

        // Configuración del WebView para mostrar la cámara
        val webView = findViewById<WebView>(R.id.webview_camera)
        setupWebView(webView)
    }

    private fun toggleFlash(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                withContext(Dispatchers.Main) {
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.d("CameraActivity", "Flash toggle successful: $url")
                    } else {
                        Log.e("CameraActivity", "Error toggling flash: Response code $responseCode")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("CameraActivity", "Exception toggling flash: ${e.message}")
                }
            }
        }
    }

    private fun setupWebView(webView: WebView) {
        webView.settings.apply {
            javaScriptEnabled = true // Habilita JavaScript para el contenido
            loadWithOverviewMode = true // Ajusta el contenido al tamaño del WebView
            useWideViewPort = true // Habilita la visualización a ancho completo
            domStorageEnabled = true // Habilita el almacenamiento DOM
            mediaPlaybackRequiresUserGesture = false // Permite la reproducción automática
            cacheMode = WebSettings.LOAD_NO_CACHE // Desactiva el almacenamiento en caché
        }

        webView.isHorizontalScrollBarEnabled = true // Deshabilita el scroll horizontal
        webView.isVerticalScrollBarEnabled = false // Deshabilita el scroll vertical

        webView.setOnTouchListener { _, _ -> false } // Desactiva la interacción de toque

        // Asigna un WebViewClient para que cargue el contenido dentro del WebView
        webView.webViewClient = WebViewClient()

        // Carga la URL de la cámara
        val cameraUrl = "https://d9c8-190-108-95-235.ngrok-free.app/felicam/1" // Reemplaza <IP_DE_TU_ESP32> con la IP de tu ESP32-CAM
        webView.loadUrl(cameraUrl)
    }
}
