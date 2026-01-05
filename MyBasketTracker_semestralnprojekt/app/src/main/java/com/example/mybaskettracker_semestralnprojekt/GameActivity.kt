package com.example.mybaskettracker_semestralniprojekt // <--- ZMĚNA ZDE

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// Import musí odpovídat novému názvu balíčku
import com.example.mybaskettracker_semestralniprojekt.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private var scoreHome = 0
    private var scoreAway = 0
    private var homeName = ""
    private var awayName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeName = intent.getStringExtra("HOME_NAME") ?: "Domácí"
        awayName = intent.getStringExtra("AWAY_NAME") ?: "Hosté"
        val logoUriString = intent.getStringExtra("HOME_LOGO")

        binding.tvHomeNameGame.text = homeName
        binding.tvAwayNameGame.text = awayName

        if (logoUriString != null) {
            binding.ivGameHomeLogo.setImageURI(Uri.parse(logoUriString))
        }

        binding.btnHome1.setOnClickListener { updateHomeScore(1) }
        binding.btnHome2.setOnClickListener { updateHomeScore(2) }
        binding.btnHome3.setOnClickListener { updateHomeScore(3) }

        binding.btnAway1.setOnClickListener { updateAwayScore(1) }
        binding.btnAway2.setOnClickListener { updateAwayScore(2) }
        binding.btnAway3.setOnClickListener { updateAwayScore(3) }

        binding.btnEndGame.setOnClickListener {
            // Tady to házelo chybu, protože ResultActivity.kt asi ještě nemá správný balíček
            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("WINNER_SCORE_HOME", scoreHome)
            intent.putExtra("WINNER_SCORE_AWAY", scoreAway)
            intent.putExtra("TEAM_HOME", homeName)
            intent.putExtra("TEAM_AWAY", awayName)

            startActivity(intent)
            finish()
        }
    }

    private fun updateHomeScore(points: Int) {
        scoreHome += points
        binding.tvHomeScore.text = scoreHome.toString()
    }

    private fun updateAwayScore(points: Int) {
        scoreAway += points
        binding.tvAwayScore.text = scoreAway.toString()
    }
}