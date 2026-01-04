package com.example.ukol_4 // <--- ZMĚNA ZDE

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ukol_4.databinding.ActivityThirdBinding // <--- ZMĚNA ZDE

class ThirdActivity : AppCompatActivity() {


    private lateinit var binding: ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Top Bar
        supportActionBar?.title = "Finále"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Příjem finálních dat
        val finalniText = intent.getStringExtra("FINALNI_DATA")

        binding.tvFinalResult.text = finalniText
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}