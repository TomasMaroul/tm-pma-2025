package com.example.mybaskettracker_semestralniprojekt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Důležité pro databázi na pozadí
import com.example.mybaskettracker_semestralniprojekt.databinding.ActivityResultBinding
import kotlinx.coroutines.launch // Spouštění úloh

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val scoreHome = intent.getIntExtra("WINNER_SCORE_HOME", 0)
        val scoreAway = intent.getIntExtra("WINNER_SCORE_AWAY", 0)
        val homeName = intent.getStringExtra("TEAM_HOME") ?: "Domácí"
        val awayName = intent.getStringExtra("TEAM_AWAY") ?: "Hosté"


        if (scoreHome > scoreAway) {
            binding.tvWinnerName.text = homeName
            binding.tvWinnerName.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
        } else if (scoreAway > scoreHome) {
            binding.tvWinnerName.text = awayName
            binding.tvWinnerName.setTextColor(android.graphics.Color.parseColor("#D32F2F"))
        } else {
            binding.tvWinnerName.text = "REMÍZA!"
            binding.tvWinnerName.setTextColor(android.graphics.Color.GRAY)
        }
        binding.tvFinalScore.text = "$scoreHome : $scoreAway"





        val matchTitle = "$homeName vs $awayName"

        val matchScore = "$scoreHome : $scoreAway"


        val novyZapas = MatchRecord(
            opponent = matchTitle,
            score = matchScore,
            created = System.currentTimeMillis(), // Aktuální čas
            category = "Streetball" // Zatím natvrdo, později můžeme měnit
        )


        lifecycleScope.launch {
            try {
                val database = AppDatabase.getDatabase(this@ResultActivity)
                database.matchDao().insert(novyZapas)

                // Malé potvrzení pro vás (vydrží chvíli)
                Toast.makeText(this@ResultActivity, "Zápas uložen do historie! 💾", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ResultActivity, "Chyba ukládání: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnBackToMenu.setOnClickListener {
            finish()
        }
    }
}