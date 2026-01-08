package com.example.pma_projekt_basketstats

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import data.AppDatabase
import data.Match
import kotlinx.coroutines.launch


class AddMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_match)

        val etHomeTeam = findViewById<EditText>(R.id.etHomeTeam)
        val etAwayTeam = findViewById<EditText>(R.id.etAwayTeam)
        val etHomeScore = findViewById<EditText>(R.id.etHomeScore)
        val etAwayScore = findViewById<EditText>(R.id.etAwayScore)
        val btnSave = findViewById<Button>(R.id.btnSaveMatch)

        btnSave.setOnClickListener {
            val homeT = etHomeTeam.text.toString()
            val awayT = etAwayTeam.text.toString()
            val homeS = etHomeScore.text.toString().toIntOrNull() ?: 0
            val awayS = etAwayScore.text.toString().toIntOrNull() ?: 0

            if (homeT.isNotEmpty() && awayT.isNotEmpty()) {
                val match = Match(
                    homeTeam = homeT,
                    awayTeam = awayT,
                    homeScore = homeS,
                    awayScore = awayS
                )
                saveMatchToDb(match)
            } else {
                Toast.makeText(this, "Vyplňte názvy týmů!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveMatchToDb(match: Match) {
        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            db.matchDao().insert(match)
            Toast.makeText(this@AddMatchActivity, "Zápas uložen", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}