package com.upao.felihouse.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.upao.felihouse.R
import com.upao.felihouse.com.upao.felihouse.ui.data.Event
import com.upao.felihouse.com.upao.felihouse.ui.data.EventAdapter
import java.util.TimeZone

class EventLogActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private val eventList = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_event_log, findViewById(R.id.container))

        recyclerView = findViewById(R.id.event_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(eventList)
        recyclerView.adapter = adapter

        // Cambiar el color del status bar dinámicamente
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.melon)
        }

        fetchEventsFromFirestore()
    }

    private fun fetchEventsFromFirestore() {
        FirebaseFirestore.getInstance()
            .collection("eventos")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { documents ->
                eventList.clear() // Limpia la lista antes de agregar nuevos elementos
                for (document in documents) {
                    val timestamp = document.getLong("timestamp") ?: 0L
                    val estado = document.getString("estado") ?: "Desconocido"
                    val tipo = document.getString("tipo") ?: "Desconocido"

                    // Crear un objeto Event
                    val event = Event(
                        timestamp = timestamp,
                        estado = estado,
                        tipo = tipo
                    )
                    eventList.add(event) // Agregar el evento a la lista
                }
                adapter.notifyDataSetChanged() // Notificar cambios al adaptador
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al recuperar eventos", e)
            }
    }


    fun Long.toPeruFormattedDate(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("America/Lima") // Configura la zona horaria de Perú
        return sdf.format(this)
    }
}