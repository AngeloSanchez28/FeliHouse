package com.upao.felihouse.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.upao.felihouse.R

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Configurar el DrawerLayout y NavigationView
        drawerLayout = findViewById(R.id.main)
        navigationView = findViewById(R.id.navigation_view)

        // Configurar Toolbar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar el botón de menú del DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Manejar clics en las opciones del menú
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_camera -> {
                    startActivity(Intent(this, CameraActivity::class.java))
                    drawerLayout.closeDrawers() // Cierra el Drawer
                    true
                }
                R.id.nav_event_log -> {
                    startActivity(Intent(this, EventLogActivity::class.java))
                    drawerLayout.closeDrawers() // Cierra el Drawer
                    true
                }
                else -> false
            }
        }

    }
}
