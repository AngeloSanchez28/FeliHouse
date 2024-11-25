package com.upao.felihouse.com.upao.felihouse.ui.data

import java.util.TimeZone

data class Event(
    val timestamp: Long = 0,
    val estado: String = "",
    val tipo: String = ""
) {
    fun formatTimestamp(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("America/Lima")
        return sdf.format(java.util.Date(timestamp))
    }
}
