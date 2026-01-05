package com.example.ukol_8_sharedpreferences  // <--- ZDE BYLA PRAVDĚPODOBNĚ CHYBA

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// DŮLEŽITÉ: Import musí odpovídat názvu vašeho projektu (Ukol 8)
import com.example.ukol_8_sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Přístup k SharedPreferences
        val sharedPreferences = getSharedPreferences("MojeData", Context.MODE_PRIVATE)

        // --- Tlačítko ULOŽIT ---
        binding.btnSave.setOnClickListener {
            val jmeno = binding.etName.text.toString()
            val vek = binding.etAge.text.toString()
            val jeDospely = binding.cbAdult.isChecked

            val editor = sharedPreferences.edit()
            editor.putString("klic_jmeno", jmeno)
            editor.putString("klic_vek", vek)
            editor.putBoolean("klic_dospely", jeDospely)
            editor.apply()

            Toast.makeText(this, "Data uložena ✅", Toast.LENGTH_SHORT).show()
        }

        // --- Tlačítko NAČÍST ---
        binding.btnLoad.setOnClickListener {
            val nacteneJmeno = sharedPreferences.getString("klic_jmeno", "")
            val nactenyVek = sharedPreferences.getString("klic_vek", "")
            val nactenoDospely = sharedPreferences.getBoolean("klic_dospely", false)

            binding.etName.setText(nacteneJmeno)
            binding.etAge.setText(nactenyVek)
            binding.cbAdult.isChecked = nactenoDospely

            Toast.makeText(this, "Data načtena 🔄", Toast.LENGTH_SHORT).show()
        }
    }
}