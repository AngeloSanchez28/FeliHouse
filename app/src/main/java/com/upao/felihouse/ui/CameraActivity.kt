package com.upao.felihouse.ui

import android.os.Build
import android.os.Bundle
import com.upao.felihouse.R

class CameraActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_camera, findViewById(R.id.container))
        // Cambiar el color del status bar dinámicamente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.melon) // Cambia R.color.dashboard_color
        }
    }
}
