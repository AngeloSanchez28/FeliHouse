package com.upao.felihouse.com.upao.felihouse.ui.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upao.felihouse.R

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timestampText: TextView = itemView.findViewById(R.id.timestamp_text)
        val typeText: TextView = itemView.findViewById(R.id.type_text)
        val stateText: TextView = itemView.findViewById(R.id.state_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.timestampText.text = event.formatTimestamp() // Formatea el timestamp
        holder.typeText.text = event.tipo
        holder.stateText.text = event.estado
    }


    override fun getItemCount() = events.size
}