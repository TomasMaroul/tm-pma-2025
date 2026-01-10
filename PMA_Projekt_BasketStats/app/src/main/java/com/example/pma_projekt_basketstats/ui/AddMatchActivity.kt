package com.example.pma_projekt_basketstats.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pma_projekt_basketstats.data.local.AppDatabase
import com.example.pma_projekt_basketstats.data.local.TeamDataStore
import com.example.pma_projekt_basketstats.data.model.Match
import com.example.pma_projekt_basketstats.databinding.ActivityAddMatchBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddMatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMatchBinding
    private var matchId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        matchId = intent.getIntExtra("MATCH_ID", 0)
        setupTeamSpinners()

        if (matchId != 0) {
            binding.tvTitle.text = "Upravit zápas"
            loadMatchData()
        }

        binding.btnSaveMatch.setOnClickListener {
            saveMatch()
        }
    }

    private fun setupTeamSpinners() {
        val dataStore = TeamDataStore(this)
        lifecycleScope.launch {
            val teams = dataStore.teams.first().toList()
            val adapter = ArrayAdapter(this@AddMatchActivity, android.R.layout.simple_spinner_item, teams)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerHomeTeam.adapter = adapter
            binding.spinnerAwayTeam.adapter = adapter
        }
    }

    private fun loadMatchData() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@AddMatchActivity)
            val match = db.matchDao().getMatchById(matchId)
            match?.let {
                binding.etHomeScore.setText(it.homeScore.toString())
                binding.etAwayScore.setText(it.awayScore.toString())
                binding.etHomeFGMade.setText(it.homeFGMade.toString())
                binding.etHomeFGAtt.setText(it.homeFGAttempted.toString())
                binding.etAwayFGMade.setText(it.awayFGMade.toString())
                binding.etAwayFGAtt.setText(it.awayFGAttempted.toString())
                binding.etHome3PMade.setText(it.home3PMade.toString())
                binding.etHome3PAtt.setText(it.home3PAttempted.toString())
                binding.etAway3PMade.setText(it.away3PMade.toString())
                binding.etAway3PAtt.setText(it.away3PAttempted.toString())
                binding.etScorers.setText(it.scorers)
                
                val dataStore = TeamDataStore(this@AddMatchActivity)
                val teams = dataStore.teams.first().toList()
                val homeIndex = teams.indexOf(it.homeTeam)
                val awayIndex = teams.indexOf(it.awayTeam)
                if (homeIndex >= 0) binding.spinnerHomeTeam.setSelection(homeIndex)
                if (awayIndex >= 0) binding.spinnerAwayTeam.setSelection(awayIndex)
            }
        }
    }

    private fun saveMatch() {
        val homeTeam = binding.spinnerHomeTeam.selectedItem?.toString() ?: ""
        val awayTeam = binding.spinnerAwayTeam.selectedItem?.toString() ?: ""
        
        if (homeTeam == awayTeam) {
            Toast.makeText(this, "Vyberte různé týmy!", Toast.LENGTH_SHORT).show()
            return
        }

        val match = Match(
            id = matchId,
            homeTeam = homeTeam,
            awayTeam = awayTeam,
            homeScore = binding.etHomeScore.text.toString().toIntOrNull() ?: 0,
            awayScore = binding.etAwayScore.text.toString().toIntOrNull() ?: 0,
            homeFGMade = binding.etHomeFGMade.text.toString().toIntOrNull() ?: 0,
            homeFGAttempted = binding.etHomeFGAtt.text.toString().toIntOrNull() ?: 0,
            awayFGMade = binding.etAwayFGMade.text.toString().toIntOrNull() ?: 0,
            awayFGAttempted = binding.etAwayFGAtt.text.toString().toIntOrNull() ?: 0,
            home3PMade = binding.etHome3PMade.text.toString().toIntOrNull() ?: 0,
            home3PAttempted = binding.etHome3PAtt.text.toString().toIntOrNull() ?: 0,
            away3PMade = binding.etAway3PMade.text.toString().toIntOrNull() ?: 0,
            away3PAttempted = binding.etAway3PAtt.text.toString().toIntOrNull() ?: 0,
            scorers = binding.etScorers.text.toString()
        )

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@AddMatchActivity)
            if (matchId == 0) db.matchDao().insert(match) else db.matchDao().update(match)
            finish()
        }
    }
}