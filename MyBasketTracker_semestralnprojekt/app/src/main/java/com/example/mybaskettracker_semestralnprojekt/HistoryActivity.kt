package com.example.mybaskettracker_semestralniprojekt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybaskettracker_semestralniprojekt.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení seznamu
        binding.rvMatches.layoutManager = LinearLayoutManager(this)

        // Načtení dat z databáze na pozadí
        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@HistoryActivity)
            val matchesList = database.matchDao().getAllMatches()

            // Naplnění adapteru
            val adapter = MatchesAdapter(matchesList)
            binding.rvMatches.adapter = adapter
        }
    }
}