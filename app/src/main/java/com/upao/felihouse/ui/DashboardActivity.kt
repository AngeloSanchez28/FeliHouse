package com.upao.felihouse.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.upao.felihouse.R
import com.upao.felihouse.ui.Connection.ApiService
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.TimeZone

class DashboardActivity : BaseActivity() {

    private var esAccionGlobal = false // Control para acciones globales
    private var isInitializing = true // Evitar registrar eventos al cargar la interfaz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_dashboard, findViewById(R.id.container))

        // Cambiar el color del status bar dinámicamente
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.melon)
        }

        getEstadosDeCuartos()

        // Configura los switches
        setupSwitches()
    }

    private fun setupSwitches() {
        val switchMasterRoom = findViewById<SwitchCompat>(R.id.switch_master_room)
        val switchSecondaryRoom = findViewById<SwitchCompat>(R.id.switch_secondary_room)
        val switchSecondaryRoom2 = findViewById<SwitchCompat>(R.id.switch_secondary_room2)
        val switchLivingRoom = findViewById<SwitchCompat>(R.id.switch_living_room)
        val switchHallway = findViewById<SwitchCompat>(R.id.switch_hallway)
        val switchExterior = findViewById<SwitchCompat>(R.id.switch_exterior)
        val switchBathroom = findViewById<SwitchCompat>(R.id.switch_bathroom)
        val switchGarage = findViewById<SwitchCompat>(R.id.switch_garage)
        val switchCocina = findViewById<SwitchCompat>(R.id.cocina)
        val switchAllLights = findViewById<SwitchCompat>(R.id.switch_all_lights) // Switch para todos los LEDs

        // Configurar el switch de todos los LEDs
        configureAllLightsSwitch(switchAllLights)

        configureSwitch(switchMasterRoom, "cuartoprincipalon", "cuartoprincipaloff")
        configureSwitch(switchSecondaryRoom, "cuarto1on", "cuarto1off")
        configureSwitch(switchSecondaryRoom2, "cuarto2on", "cuarto2off")
        configureSwitch(switchLivingRoom, "salaon", "salaoff")
        configureSwitch(switchHallway, "pasadisoon", "pasadisooff")
        configureSwitch(switchExterior, "jardinon", "jardinoff")
        configureSwitch(switchBathroom, "banoon", "banooff")
        configureSwitch(switchGarage, "cocheraon", "cocheraoff")
        configureSwitch(switchCocina, "cocinaon", "cocinaoff")
    }

    private fun configureSwitch(switch: SwitchCompat, endpointOn: String, endpointOff: String) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isInitializing || esAccionGlobal) return@setOnCheckedChangeListener // Ignora durante inicialización o acción global
            val endpoint = if (isChecked) endpointOn else endpointOff
            toggleLight(endpoint, isChecked)
        }
    }

    private fun getEstadosDeCuartos() {
        isInitializing = true

        Firebase.firestore.collection("cuartos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tipoCuarto = document.id
                    val estado = document.getString("estado") ?: "Apagado"

                    // Configura los switches según el estado
                    when (tipoCuarto) {
                        "Cuarto Principal" -> findViewById<SwitchCompat>(R.id.switch_master_room).isChecked = (estado == "Encendido")
                        "Cuarto Secundario 1" -> findViewById<SwitchCompat>(R.id.switch_secondary_room).isChecked = (estado == "Encendido")
                        "Cuarto Secundario 2" -> findViewById<SwitchCompat>(R.id.switch_secondary_room2).isChecked = (estado == "Encendido")
                        "Sala" -> findViewById<SwitchCompat>(R.id.switch_living_room).isChecked = (estado == "Encendido")
                        "Cocina" -> findViewById<SwitchCompat>(R.id.cocina).isChecked = (estado == "Encendido")
                        "Pasadizo" -> findViewById<SwitchCompat>(R.id.switch_hallway).isChecked = (estado == "Encendido")
                        "Jardín" -> findViewById<SwitchCompat>(R.id.switch_exterior).isChecked = (estado == "Encendido")
                        "Baño" -> findViewById<SwitchCompat>(R.id.switch_bathroom).isChecked = (estado == "Encendido")
                        "Cochera" -> findViewById<SwitchCompat>(R.id.switch_garage).isChecked = (estado == "Encendido")
                    }
                }

                isInitializing = false
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al obtener estados de los cuartos: ${e.message}", e)
            }
    }


    private fun configureAllLightsSwitch(switch: SwitchCompat) {
        // Obtener el estado global al cargar la interfaz
        Firebase.firestore.collection("cuartos").document("global")
            .get()
            .addOnSuccessListener { document ->
                val encenderTodo = document.getBoolean("encenderTodo") ?: false
                switch.isChecked = encenderTodo // Actualiza el switch con el estado global
            }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (esAccionGlobal) return@setOnCheckedChangeListener // Evita acciones durante una acción global
            val endpoint = if (isChecked) "allon" else "alloff"
            toggleAllLights(endpoint)
        }
    }

    private fun guardarEvento(endpoint: String, isOn: Boolean) {
        // Obtener la hora actual ajustada a la zona horaria de Perú
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Lima"))
        val currentTimeInMillis = calendar.timeInMillis

        val tipoCuarto = when (endpoint) {
            "cuartoprincipalon", "cuartoprincipaloff" -> "Cuarto Principal"
            "cuarto1on", "cuarto1off" -> "Cuarto Secundario 1"
            "cuarto2on", "cuarto2off" -> "Cuarto Secundario 2"
            "cocinaon", "cocinaoff" -> "Cocina"
            "salaon", "salaoff" -> "Sala"
            "pasadisoon", "pasadisooff" -> "Pasadizo"
            "jardinon", "jardinoff" -> "Jardín"
            "banoon", "banooff" -> "Baño"
            "cocheraon", "cocheraoff" -> "Cochera"
            else -> "Desconocido"
        }

        Log.d("Firestore", "Entrando a guardarEvento con endpoint: $endpoint y estado: $isOn")

        val evento = mapOf(
            "timestamp" to Calendar.getInstance(TimeZone.getTimeZone("America/Lima")).timeInMillis,
            "estado" to if (isOn) "Encendido" else "Apagado",
            "tipo" to tipoCuarto
        )
        Firebase.firestore.collection("eventos")
            .add(evento)
            .addOnSuccessListener {
                Log.d("Firestore", "Evento guardado correctamente")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al guardar el evento: ${e.message}", e)
            }

        // Actualizar el estado actual del cuarto en Firestore
        val estadoActual = mapOf(
            "estado" to if (isOn) "Encendido" else "Apagado"
        )
        Firebase.firestore.collection("cuartos").document(tipoCuarto)
            .set(estadoActual)
            .addOnSuccessListener {
                Log.d("Firestore", "Estado del cuarto actualizado: $tipoCuarto -> ${if (isOn) "Encendido" else "Apagado"}")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al actualizar estado del cuarto: ${e.message}", e)
            }
    }

    private fun toggleLight(endpoint: String, isChecked: Boolean) {
        // Aquí debes definir cómo interactuar con la API para encender o apagar una luz
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when (endpoint) {
                    "cuartoprincipalon" -> ApiService.apiEndpoint.encenderCuartoPrincipal()
                    "cuartoprincipaloff" -> ApiService.apiEndpoint.apagarCuartoPrincipal()
                    "cuarto1on" -> ApiService.apiEndpoint.encenderCuarto1()
                    "cuarto1off" -> ApiService.apiEndpoint.apagarCuarto1()
                    "cuarto2on" -> ApiService.apiEndpoint.encenderCuarto2()
                    "cuarto2off" -> ApiService.apiEndpoint.apagarCuarto2()
                    "cocinaon" -> ApiService.apiEndpoint.encenderCocina()
                    "cocinaoff" -> ApiService.apiEndpoint.apagarCocina()
                    "salaon" -> ApiService.apiEndpoint.encenderSala()
                    "salaoff" -> ApiService.apiEndpoint.apagarSala()
                    "pasadisoon" -> ApiService.apiEndpoint.encenderPasadiso()
                    "pasadisooff" -> ApiService.apiEndpoint.apagarPasadiso()
                    "jardinon" -> ApiService.apiEndpoint.encenderJardin()
                    "jardinoff" -> ApiService.apiEndpoint.apagarJardin()
                    "banoon" -> ApiService.apiEndpoint.encenderBano()
                    "banooff" -> ApiService.apiEndpoint.apagarBano()
                    "cocheraon" -> ApiService.apiEndpoint.encenderCochera()
                    "cocheraoff" -> ApiService.apiEndpoint.apagarCochera()
                    else -> null
                }

                if (response != null && response.isSuccessful) {
                    // Si la solicitud fue exitosa, puedes hacer algo con la respuesta, por ejemplo:
                    withContext(Dispatchers.Main) {
                        // Actualizar la UI si es necesario
                        Log.d("DashboardActivity", "Luz cambiada correctamente")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(
                        findViewById(R.id.container),
                        "Error: ${e.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun toggleAllLights(endpoint: String) {
        esAccionGlobal = true // Inicia la acción global
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when (endpoint) {
                    "allon" -> ApiService.apiEndpoint.encenderTodo()
                    "alloff" -> ApiService.apiEndpoint.apagarTodo()
                    else -> null
                }

                if (response != null && response.isSuccessful) {
                    val isOn = endpoint == "allon"
                    val estado = if (isOn) "Encendido" else "Apagado"

                    withContext(Dispatchers.Main) {
                        // Actualizar todos los switches
                        val switches = listOf(
                            findViewById<SwitchCompat>(R.id.switch_master_room),
                            findViewById<SwitchCompat>(R.id.switch_secondary_room),
                            findViewById<SwitchCompat>(R.id.switch_secondary_room2),
                            findViewById<SwitchCompat>(R.id.switch_living_room),
                            findViewById<SwitchCompat>(R.id.switch_hallway),
                            findViewById<SwitchCompat>(R.id.switch_exterior),
                            findViewById<SwitchCompat>(R.id.switch_bathroom),
                            findViewById<SwitchCompat>(R.id.switch_garage),
                            findViewById<SwitchCompat>(R.id.cocina)
                        )
                        switches.forEach { it.isChecked = isOn }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(
                        findViewById(R.id.container),
                        "Error: ${e.message}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } finally {
                esAccionGlobal = false // Finaliza la acción global
            }
        }
    }

}