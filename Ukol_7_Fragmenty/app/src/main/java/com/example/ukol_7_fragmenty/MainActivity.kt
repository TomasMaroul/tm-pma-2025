package com.example.ukol_7_fragmenty  // <--- ZMĚNA ZDE

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
// Import pro Binding musí odpovídat názvu balíčku
import com.example.ukol_7_fragmenty.databinding.ActivityMainBinding // <--- ZMĚNA ZDE

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Na začátku zobrazíme třeba Den
        vymenitFragment(DayFragment())

        // Kliknutí na DEN
        binding.btnDay.setOnClickListener {
            vymenitFragment(DayFragment())
        }

        // Kliknutí na NOC
        binding.btnNight.setOnClickListener {
            vymenitFragment(NightFragment())
        }
    }

    private fun vymenitFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}