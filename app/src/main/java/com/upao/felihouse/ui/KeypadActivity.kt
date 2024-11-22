package com.upao.felihouse.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.upao.felihouse.R
import java.net.URI

class KeypadActivity : BaseActivity() {

    private var webSocketClient: WebSocketHelper? = null
    private lateinit var displayKey: TextView
    private lateinit var doorStateView: TextView
    private lateinit var historyView: TextView
    private var enteredKey: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_keypad, findViewById(R.id.container))

        // Inicializar vistas
        displayKey = findViewById(R.id.display_key)
        doorStateView = findViewById(R.id.keypad_title) // Utilizado para mostrar el estado de la puerta
        historyView = TextView(this) // Historial dinámico si necesitas agregar uno

        // Configurar WebSocket
        connectWebSocket()

        // Configurar botones del teclado
        val buttonIds = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7,
            R.id.button_8, R.id.button_9
        )
        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { appendKey((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.button_clear).setOnClickListener { clearKey() }
        findViewById<Button>(R.id.button_enter).setOnClickListener { sendKey() }
    }

    private fun connectWebSocket() {
        val serverUri = URI("ws://192.168.4.1:81/")
        webSocketClient = WebSocketHelper(serverUri)

        webSocketClient?.onMessageReceived = { color, historial, clave ->
            runOnUiThread {
                try {
                    // Actualizar estado visual
                    updateVisualState(color, historial, clave)
                } catch (e: Exception) {
                    Log.e("WebSocket", "Error al actualizar la interfaz: ${e.message}")
                }
            }
        }

        webSocketClient?.connect()
    }

    private fun updateVisualState(color: String, historial: String, clave: String) {
        // Cambiar color del título según el estado de la puerta
        doorStateView.text = if (color == "green") "Puerta abierta" else "Puerta cerrada"
        doorStateView.setTextColor(
            ContextCompat.getColor(this, if (color == "green") R.color.verde else R.color.rojo)
        )

        // Actualizar historial
        Log.d("Historial", historial) // Mostrar historial en Logcat o implementarlo en pantalla

        // Actualizar clave ingresada
        displayKey.text = "Clave ingresada: $clave"
    }

    private fun appendKey(key: String) {
        if (enteredKey.length < 4) {
            enteredKey += key
            displayKey.text = enteredKey

            // Envía la tecla ingresada al servidor
            sendSingleKey(key)
        }
    }

    private fun sendSingleKey(key: String) {
        val client = webSocketClient
        if (client?.isOpen == true) {
            try {
                client.send("tecla$key")
                Log.d("WebSocket", "Tecla enviada: tecla$key")
            } catch (e: Exception) {
                Log.e("WebSocket", "Error al enviar tecla: ${e.message}")
            }
        } else {
            displayKey.text = "Conexión no establecida"
        }
    }

    private fun clearKey() {
        enteredKey = ""
        displayKey.text = ""
        sendClearCommand()
    }

    private fun sendClearCommand() {
        val client = webSocketClient
        if (client?.isOpen == true) {
            try {
                client.send("tecla*")
                Log.d("WebSocket", "Comando de borrado enviado")
            } catch (e: Exception) {
                Log.e("WebSocket", "Error al enviar comando de borrado: ${e.message}")
            }
        }
    }

    private fun sendKey() {
        if (enteredKey.length == 4) { // Verifica que el código tiene 4 dígitos
            val client = webSocketClient
            if (client?.isOpen == true) {
                try {
                    client.send("tecla#")
                    Log.d("WebSocket", "Código enviado para verificación: tecla#$enteredKey")

                    // Limpia el estado después de enviar
                    clearKey()
                } catch (e: Exception) {
                    Log.e("WebSocket", "Error al enviar código: ${e.message}")
                }
            } else {
                displayKey.text = "Conexión no establecida"
            }
        } else {
            displayKey.text = "Código incompleto"
            Log.d("WebSocket", "Intento de envío con código incompleto: $enteredKey")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketClient?.close()
    }
}
