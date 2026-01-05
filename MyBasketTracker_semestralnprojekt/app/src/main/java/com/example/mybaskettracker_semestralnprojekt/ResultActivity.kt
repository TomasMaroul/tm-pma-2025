package com.example.mybaskettracker_semestralniprojekt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mybaskettracker_semestralniprojekt.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Vybalíme data z "batůžku" (Intentu)
        val scoreHome = intent.getIntExtra("WINNER_SCORE_HOME", 0)
        val scoreAway = intent.getIntExtra("WINNER_SCORE_AWAY", 0)
        val homeName = intent.getStringExtra("TEAM_HOME") ?: "Domácí"
        val awayName = intent.getStringExtra("TEAM_AWAY") ?: "Hosté"

        // 2. Rozhodneme, kdo vyhrál
        if (scoreHome > scoreAway) {
            binding.tvWinnerName.text = homeName
            binding.tvWinnerName.setTextColor(android.graphics.Color.parseColor("#4CAF50")) // Zelená
        } else if (scoreAway > scoreHome) {
            binding.tvWinnerName.text = awayName
            binding.tvWinnerName.setTextColor(android.graphics.Color.parseColor("#D32F2F")) // Červená
        } else {
            binding.tvWinnerName.text = "REMÍZA!"
            binding.tvWinnerName.setTextColor(android.graphics.Color.GRAY)
        }

        // 3. Zobrazíme skóre
        binding.tvFinalScore.text = "$scoreHome : $scoreAway"

        // 4. Tlačítko zpět do menu
        binding.btnBackToMenu.setOnClickListener {
            // Protože jsme GameActivity zavřeli pomocí finish(), stačí zavřít i tuto aktivitu
            // a automaticky se ocitneme zpátky v MainActivity (která zůstala otevřená vespod).
            finish()
        }
    }
}