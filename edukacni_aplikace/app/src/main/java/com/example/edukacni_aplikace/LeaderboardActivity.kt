package com.example.edukacni_aplikace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edukacni_aplikace.databinding.ActivityLeaderboardBinding
import kotlinx.coroutines.launch

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeaderboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvLeaderboard.layoutManager = LinearLayoutManager(this)


        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@LeaderboardActivity)

            val topPlayers = database.quizDao().getTopPlayers()


            val adapter = LeaderboardAdapter(topPlayers)
            binding.rvLeaderboard.adapter = adapter
        }
    }
}