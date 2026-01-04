package com.example.ukol_4  // <--- ZMĚNA ZDE

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ukol_4.databinding.ActivityMainBinding // <--- ZMĚNA ZDE

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení titulku Top Baru
        supportActionBar?.title = "Hlavní Aktivita"

        binding.btnGoToSecond.setOnClickListener {
            val jmeno = binding.etName.text.toString()
            val vzkaz = binding.etMessage.text.toString()

            // Vytvoření Intentu (Úmyslu přejít na SecondActivity)
            val intent = Intent(this, SecondActivity::class.java)

            // "Zabalení" dat do batůžku (Key - Value)
            intent.putExtra("KLIC_JMENO", jmeno)
            intent.putExtra("KLIC_VZKAZ", vzkaz)

            // Start
            startActivity(intent)
        }
    }
}