package com.example.ukol_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Najdeme prvky
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Kliknutí na Přihlásit
        btnLogin.setOnClickListener {
            val jmeno = etUsername.text.toString()
            val heslo = etPassword.text.toString()

            if (jmeno.isNotEmpty() && heslo.isNotEmpty()) {
                Toast.makeText(this, "Vítejte, $jmeno!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Vyplňte jméno a heslo", Toast.LENGTH_SHORT).show()
            }
        }

        // Kliknutí na Registrace (jen na ukázku)
        btnRegister.setOnClickListener {
            Toast.makeText(this, "Funkce registrace zatím není dostupná", Toast.LENGTH_SHORT).show()
        }
    }
}