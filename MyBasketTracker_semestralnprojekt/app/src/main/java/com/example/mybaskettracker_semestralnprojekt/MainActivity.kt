package com.example.mybaskettracker_semestralniprojekt // <--- ZMĚNA ZDE

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
// Import musí odpovídat novému názvu balíčku
import com.example.mybaskettracker_semestralniprojekt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedImageUri: Uri? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivTeamLogo.setImageURI(uri)
            selectedImageUri = uri
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectLogo.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnStartGame.setOnClickListener {
            val homeTeam = binding.etHomeTeam.text.toString()
            val awayTeam = binding.etAwayTeam.text.toString()

            if (homeTeam.isEmpty() || awayTeam.isEmpty()) {
                Toast.makeText(this, "Vyplňte názvy obou týmů!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("HOME_NAME", homeTeam)
                intent.putExtra("AWAY_NAME", awayTeam)

                if (selectedImageUri != null) {
                    intent.putExtra("HOME_LOGO", selectedImageUri.toString())
                }

                startActivity(intent)
            }
        }
    }
}