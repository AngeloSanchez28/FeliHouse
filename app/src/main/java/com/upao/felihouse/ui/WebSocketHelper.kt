package com.upao.felihouse.ui

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONException
import org.json.JSONObject
import java.net.URI

class WebSocketHelper(serverUri: URI) : WebSocketClient(serverUri) {

    var onConnectionOpened: (() -> Unit)? = null // Callback cuando se abre la conexión
    var onMessageReceived: ((color: String, historial: String, clave: String) -> Unit)? = null // Callback para mensajes

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d("WebSocket", "Conexión establecida con el ESP32")
        onConnectionOpened?.invoke() // Notifica que el WebSocket está conectado
    }

    override fun onMessage(message: String?) {
        message?.let {
            try {
                val json = JSONObject(it)
                val color = json.getString("color")
                val historial = json.getString("historial")
                val clave = json.getString("clave")

                // Invoca el callback para notificar el mensaje
                onMessageReceived?.invoke(color, historial, clave)
            } catch (e: JSONException) {
                Log.e("WebSocket", "Error al procesar el mensaje JSON: ${e.message}")
            }
        }
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d("WebSocket", "Conexión cerrada: $reason")
    }

    override fun onError(ex: Exception?) {
        Log.e("WebSocket", "Error en WebSocket: ${ex?.message}")
    }
}


