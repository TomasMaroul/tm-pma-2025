package com.example.pma_projekt_basketstats.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pma_projekt_basketstats.data.local.AppDatabase
import com.example.pma_projekt_basketstats.data.model.Match
import com.example.pma_projekt_basketstats.data.model.TeamStanding
import com.example.pma_projekt_basketstats.databinding.ActivityMainBinding
import com.example.pma_projekt_basketstats.ui.adapter.MatchAdapter
import com.example.pma_projekt_basketstats.ui.adapter.StandingsAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var standingsAdapter: StandingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        setupTabs()

        binding.btnAddMatch.setOnClickListener {
            startActivity(Intent(this, AddMatchActivity::class.java))
        }

        binding.swipeRefresh.setOnRefreshListener {
            loadData()
        }

        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupRecyclerViews() {
        matchAdapter = MatchAdapter(
            onEdit = { match ->
                val intent = Intent(this, AddMatchActivity::class.java)
                intent.putExtra("MATCH_ID", match.id)
                startActivity(intent)
            },
            onDelete = { match ->
                showDeleteDialog(match)
            }
        )
        binding.rvMatches.layoutManager = LinearLayoutManager(this)
        binding.rvMatches.adapter = matchAdapter

        standingsAdapter = StandingsAdapter()
        binding.rvStandings.layoutManager = LinearLayoutManager(this)
        binding.rvStandings.adapter = standingsAdapter
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    binding.swipeRefresh.visibility = View.VISIBLE
                    binding.rvStandings.visibility = View.GONE
                } else {
                    binding.swipeRefresh.visibility = View.GONE
                    binding.rvStandings.visibility = View.VISIBLE
                    calculateStandings()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadData() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MainActivity)
            val matches = db.matchDao().getAllMatches()
            matchAdapter.submitList(matches)
            calculateStandings()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun calculateStandings() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MainActivity)
            val matches = db.matchDao().getAllMatches()
            val standingsMap = mutableMapOf<String, TeamStanding>()

            matches.forEach { match ->
                val home = standingsMap.getOrPut(match.homeTeam) { TeamStanding(match.homeTeam) }
                val away = standingsMap.getOrPut(match.awayTeam) { TeamStanding(match.awayTeam) }

                home.played++
                away.played++
                home.pointsFor += match.homeScore
                home.pointsAgainst += match.awayScore
                away.pointsFor += match.awayScore
                away.pointsAgainst += match.homeScore

                if (match.homeScore > match.awayScore) {
                    home.won++
                    away.lost++
                } else if (match.awayScore > match.homeScore) {
                    away.won++
                    home.lost++
                }
            }

            val sortedStandings = standingsMap.values.sortedByDescending { it.points }
            standingsAdapter.submitList(sortedStandings)
        }
    }

    private fun showDeleteDialog(match: Match) {
        AlertDialog.Builder(this)
            .setTitle("Smazat zápas")
            .setMessage("Opravdu chcete smazat tento zápas?")
            .setPositiveButton("Ano") { _, _ ->
                lifecycleScope.launch {
                    AppDatabase.getDatabase(this@MainActivity).matchDao().delete(match)
                    loadData()
                }
            }
            .setNegativeButton("Ne", null)
            .show()
    }
}