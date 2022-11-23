package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_opciones.*

class Opciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opciones)
        btnUsuarios.setOnClickListener(){
            val intent = Intent(this, listadoUsuarios::class.java)
            startActivity(intent)
        }
        btnPeliculas.setOnClickListener(){
            val intent = Intent(this, listadoPeliculas::class.java)
            startActivity(intent)
        }
    }
}