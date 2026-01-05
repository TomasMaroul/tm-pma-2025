package com.example.edukacni_aplikace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Pro práci s databází na pozadí
import com.example.edukacni_aplikace.databinding.ActivityMainBinding
import kotlinx.coroutines.launch // Spouštění úloh

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnStart.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()

            if (username.isEmpty()) {

                binding.etUsernameLayout.error = "Musíš zadat jméno!"
            } else {
                binding.etUsernameLayout.error = null // Smažeme chybu
                saveUserAndStartGame(username)
            }
        }


        binding.btnLeaderboard.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveUserAndStartGame(name: String) {

        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@MainActivity)
            val dao = database.quizDao()


            val user = dao.getUser(name)

            if (user == null) {

                val newUser = User(username = name)
                dao.insertUser(newUser)
                Toast.makeText(this@MainActivity, "Vítej nováčku, $name! 👋", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this@MainActivity, "Vítej zpět, $name! 🏆", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this@MainActivity, QuizActivity::class.java)
            intent.putExtra("USER_NAME", name) // Pošleme jméno dál
            startActivity(intent)

            finish()
        }
    }
}