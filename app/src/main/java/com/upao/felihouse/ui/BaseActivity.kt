package com.upao.felihouse.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.upao.felihouse.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Configurar navegación
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    if (this !is DashboardActivity) {
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_camera -> {
                    if (this !is CameraActivity) {
                        startActivity(Intent(this, CameraActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_event_log -> {
                    if (this !is EventLogActivity) {
                        startActivity(Intent(this, EventLogActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_keypad -> {
                    if (this !is KeypadActivity) {
                        startActivity(Intent(this, KeypadActivity::class.java))
                        finish()
                    }
                    true
                }

                else -> false
            }
        }

        // Marcar el ítem actual como seleccionado
        markCurrentMenuItem(bottomNavigationView)
    }

    private fun markCurrentMenuItem(bottomNavigationView: BottomNavigationView) {
        when (this) {
            is DashboardActivity -> bottomNavigationView.selectedItemId = R.id.nav_home
            is CameraActivity -> bottomNavigationView.selectedItemId = R.id.nav_camera
            is EventLogActivity -> bottomNavigationView.selectedItemId = R.id.nav_event_log
            is KeypadActivity -> bottomNavigationView.selectedItemId = R.id.nav_keypad
        }
    }
}
