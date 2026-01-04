package com.example.ukol_1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Najdeme prvky na obrazovce podle jejich ID
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnSend = findViewById<Button>(R.id.btnSend)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val tvHeader = findViewById<TextView>(R.id.tvHeader)

        // 2. Co se stane, když kliknu na ODESLAT
        btnSend.setOnClickListener {
            val jmeno = etName.text.toString()

            if (jmeno.isNotEmpty()) {
                // Změníme nadpis
                tvHeader.text = "Ahoj, $jmeno!"
                // Ukážeme bublinu (Toast)
                Toast.makeText(this, "Data odeslána!", Toast.LENGTH_SHORT).show()
            } else {
                // Když je jméno prázdné, vyhodíme chybu
                etName.error = "Musíte vyplnit jméno"
            }
        }

        // 3. Co se stane, když kliknu na SMAZAT
        btnCancel.setOnClickListener {
            // Vymažeme textová pole
            etName.text.clear()
            etEmail.text.clear()
            // Vrátíme původní nadpis
            tvHeader.text = "Úkol 001 - Layout"
            Toast.makeText(this, "Formulář vyčištěn", Toast.LENGTH_SHORT).show()
        }
    }
}