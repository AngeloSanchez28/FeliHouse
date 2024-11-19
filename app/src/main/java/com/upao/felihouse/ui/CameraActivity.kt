package com.upao.felihouse.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.upao.felihouse.R

class CameraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        // No conviertas vistas al tipo incorrecto. Si no necesitas este bloque, elimínalo.
        val rootView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.root_layout)
    }
}
