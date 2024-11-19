package com.upao.felihouse.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.upao.felihouse.R

class EventLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_log)

        // Si no necesitas ajustar WindowInsets, elimina este bloque
        val rootView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.root_layout)
        rootView?.setOnApplyWindowInsetsListener { view, insets ->
            insets
        }
    }
}
