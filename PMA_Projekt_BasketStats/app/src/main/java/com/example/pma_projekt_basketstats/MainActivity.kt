package com.example.pma_projekt_basketstats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import data.AppDatabase
import data.Match
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAddMatch).setOnClickListener {
            startActivity(Intent(this, AddMatchActivity::class.java))
        }

        loadMatches()
    }

    override fun onResume() {
        super.onResume()
        loadMatches()
    }

    private fun loadMatches() {
        val container = findViewById<LinearLayout>(R.id.mainMatchContainer)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val matches = db.matchDao().getAllMatches()
            container.removeAllViews()

            val inflater = LayoutInflater.from(this@MainActivity)

            for (match in matches) {
                val matchItemView = inflater.inflate(R.layout.item_match, container, false)

                val tvTeams = matchItemView.findViewById<TextView>(R.id.tvTeams)
                val tvScore = matchItemView.findViewById<TextView>(R.id.tvScore)
                val btnDelete = matchItemView.findViewById<ImageButton>(R.id.btnDelete)

                tvTeams.text = "${match.homeTeam} vs ${match.awayTeam}"
                tvScore.text = "${match.homeScore} : ${match.awayScore}"

                btnDelete.setOnClickListener {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Smazat zápas")
                        .setMessage("Opravdu chcete smazat zápas ${match.homeTeam} vs ${match.awayTeam}?")
                        .setPositiveButton("Ano") { _, _ ->
                            lifecycleScope.launch {
                                db.matchDao().delete(match)
                                loadMatches()
                            }
                        }
                        .setNegativeButton("Ne", null)
                        .show()
                }

                container.addView(matchItemView)
            }
        }
    }
}