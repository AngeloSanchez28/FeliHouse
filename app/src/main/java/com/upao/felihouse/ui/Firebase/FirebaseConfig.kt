package com.upao.felihouse.ui.Firebase

import com.google.firebase.database.FirebaseDatabase

object FirebaseConfig {
    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance().apply {
            setPersistenceEnabled(true) // Opcional: habilita la persistencia local
        }
    }
}