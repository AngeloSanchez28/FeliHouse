package com.upao.felihouse.ui

import android.os.Build
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.upao.felihouse.R

class CameraActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_camera, findViewById(R.id.container))
        // Cambiar el color del status bar dinámicamente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.melon) // Cambia R.color.dashboard_color
        }

        val lottieSwitch = findViewById<LottieAnimationView>(R.id.lottie_switch_master_room)

        var isSwitchOn = false

        lottieSwitch.setOnClickListener {
            if (isSwitchOn) {
                lottieSwitch.speed = -1f // Reproduce la animación en reversa
                lottieSwitch.playAnimation()
                isSwitchOn = false
            } else {
                lottieSwitch.speed = 1f // Reproduce la animación normalmente
                lottieSwitch.playAnimation()
                isSwitchOn = true
            }

            // Aquí puedes agregar lógica para enviar el estado (encendido/apagado) al backend
        }

    }
}
