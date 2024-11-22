package com.upao.felihouse.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.snackbar.Snackbar
import com.upao.felihouse.R
import com.upao.felihouse.ui.Connection.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_dashboard, findViewById(R.id.container))

        // Cambiar el color del status bar dinámicamente
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.melon)
        }

        // Configura los switches
        setupSwitches()

        // Configura el botón de todas las luces
        setupAllLightsButton()
    }

    private fun setupSwitches() {
        // Referencias a los switches
        val switchMasterRoom = findViewById<SwitchCompat>(R.id.switch_master_room)
        val switchSecondaryRoom = findViewById<SwitchCompat>(R.id.switch_secondary_room)
        val switchSecondaryRoom2 = findViewById<SwitchCompat>(R.id.switch_secondary_room2)
        val switchLivingRoom = findViewById<SwitchCompat>(R.id.switch_living_room)
        val switchHallway = findViewById<SwitchCompat>(R.id.switch_hallway)
        val switchExterior = findViewById<SwitchCompat>(R.id.switch_exterior)
        val switchBathroom = findViewById<SwitchCompat>(R.id.switch_bathroom)
        val switchGarage = findViewById<SwitchCompat>(R.id.switch_garage)

        // Configura eventos para cada switch
        configureSwitch(switchMasterRoom, "cuartoprincipalon", "cuartoprincipaloff")
        configureSwitch(switchSecondaryRoom, "cuarto1on", "cuarto1off")
        configureSwitch(switchSecondaryRoom2, "cuarto2on", "cuarto2off")
        configureSwitch(switchLivingRoom, "salaon", "salaoff")
        configureSwitch(switchHallway, "pasadisoon", "pasadisooff")
        configureSwitch(switchExterior, "jardinon", "jardinoff")
        configureSwitch(switchBathroom, "banoon", "banooff")
        configureSwitch(switchGarage, "cocheraon", "cocheraoff")
    }

    private fun configureSwitch(switch: SwitchCompat, endpointOn: String, endpointOff: String) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            val endpoint = if (isChecked) endpointOn else endpointOff
            toggleLight(endpoint, isChecked)
        }
    }

    private fun toggleLight(endpoint: String, isOn: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when (endpoint) {
                    "cuartoprincipalon" -> ApiService.apiEndpoint.encenderCuartoPrincipal()
                    "cuartoprincipaloff" -> ApiService.apiEndpoint.apagarCuartoPrincipal()
                    "cuarto1on" -> ApiService.apiEndpoint.encenderCuarto1()
                    "cuarto1off" -> ApiService.apiEndpoint.apagarCuarto1()
                    "cuarto2on" -> ApiService.apiEndpoint.encenderCuarto2()
                    "cuarto2off" -> ApiService.apiEndpoint.apagarCuarto2()
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
                    else -> "Error: Endpoint no encontrado"
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DashboardActivity, response, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@DashboardActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupAllLightsButton() {
        val buttonAllLights = findViewById<Button>(R.id.button_all_lights)

        // Cambia el texto del botón según el estado (encendido/apagado)
        var allLightsOn = false

        buttonAllLights.setOnClickListener {
            val endpoint = if (allLightsOn) "alloff" else "allon"
            toggleAllLights(endpoint, buttonAllLights)
            allLightsOn = !allLightsOn // Cambia el estado
        }
    }

    private fun toggleAllLights(endpoint: String, button: Button) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when (endpoint) {
                    "allon" -> ApiService.apiEndpoint.encenderTodo()
                    "alloff" -> ApiService.apiEndpoint.apagarTodo()
                    else -> "Error: Endpoint no encontrado"
                }

                withContext(Dispatchers.Main) {
                    Snackbar.make(
                        findViewById(R.id.container),
                        response,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    button.text = if (endpoint == "allon") getString(R.string.apagar_todo) else getString(R.string.encender_todo)
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
}
