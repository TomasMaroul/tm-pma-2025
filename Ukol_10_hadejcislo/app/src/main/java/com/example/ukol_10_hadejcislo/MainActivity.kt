package com.example.ukol_10_hadejcislo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

import com.example.ukol_10_hadejcislo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var tajneCislo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        vygenerujNoveCislo()


        binding.btnCheck.setOnClickListener {
            val vstup = binding.etGuess.text.toString()
            val tip = vstup.toIntOrNull()

            if (tip != null) {
                zkontrolujTip(tip)
            } else {
                binding.etGuess.error = "Zadej číslo!"
            }
        }
    }

    private fun zkontrolujTip(tip: Int) {
        if (tip == tajneCislo) {
            Toast.makeText(this, "🎉 Správně! Generuji nové číslo...", Toast.LENGTH_LONG).show()
            vygenerujNoveCislo()
            binding.etGuess.text.clear()
        } else {
            Toast.makeText(this, "Vedle! Zkus to znovu.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun vygenerujNoveCislo() {
        tajneCislo = Random.nextInt(1, 11)
        println("Tajné číslo je: $tajneCislo")
    }
}