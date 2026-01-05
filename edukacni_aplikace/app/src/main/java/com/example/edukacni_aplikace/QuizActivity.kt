package com.example.edukacni_aplikace

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edukacni_aplikace.databinding.ActivityQuizBinding
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private var currentQuestions: List<Question> = emptyList()
    private var currentIndex = 0
    private var score = 0
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)


        username = intent.getStringExtra("USER_NAME") ?: "Neznámý"
        updateScoreUI()


        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@QuizActivity)
            val dao = database.quizDao()


            if (dao.getRandomQuestions().isEmpty()) {
                insertSampleQuestions(dao)
            }


            currentQuestions = dao.getRandomQuestions()

            if (currentQuestions.isNotEmpty()) {
                showQuestion()
            } else {
                Toast.makeText(this@QuizActivity, "Chyba: Žádné otázky!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }


        binding.btnOptionA.setOnClickListener { checkAnswer(0) }
        binding.btnOptionB.setOnClickListener { checkAnswer(1) }
        binding.btnOptionC.setOnClickListener { checkAnswer(2) }
        binding.btnOptionD.setOnClickListener { checkAnswer(3) }
    }

    private fun showQuestion() {
        val question = currentQuestions[currentIndex]
        binding.tvQuestionText.text = question.text
        binding.btnOptionA.text = question.optionA
        binding.btnOptionB.text = question.optionB
        binding.btnOptionC.text = question.optionC
        binding.btnOptionD.text = question.optionD

        binding.tvQuestionCount.text = "Otázka ${currentIndex + 1}/${currentQuestions.size}"
    }

    private fun checkAnswer(selectedIndex: Int) {
        val question = currentQuestions[currentIndex]

        if (selectedIndex == question.correctAnswerIndex) {
            score++
            Toast.makeText(this, "Správně! ✅", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Chyba! ❌", Toast.LENGTH_SHORT).show()
        }

        updateScoreUI()


        currentIndex++
        if (currentIndex < currentQuestions.size) {
            showQuestion()
        } else {
            endGame()
        }
    }

    private fun updateScoreUI() {
        binding.tvUserInfo.text = "Hráč: $username | Body: $score"
    }

    private fun endGame() {
        lifecycleScope.launch {
            val database = AppDatabase.getDatabase(this@QuizActivity)
            val dao = database.quizDao()


            val user = dao.getUser(username)
            if (user != null) {

                val newGamesCount = user.gamesPlayed + 1
                val newBestScore = if (score > user.bestScore) score else user.bestScore


                val updatedUser = user.copy(gamesPlayed = newGamesCount, bestScore = newBestScore)
                dao.updateUser(updatedUser)
            }


            showGameOverDialog()
        }
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konec hry! 🏁")
            .setMessage("Získal jsi $score bodů z ${currentQuestions.size}.")
            .setPositiveButton("Zpět do menu") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }


    private suspend fun insertSampleQuestions(dao: QuizDao) {
        val samples = listOf(
            Question(text = "Kolik je 2 + 2?", optionA = "3", optionB = "4", optionC = "5", optionD = "6", correctAnswerIndex = 1),
            Question(text = "Hlavní město ČR?", optionA = "Brno", optionB = "Ostrava", optionC = "Praha", optionD = "Plzeň", correctAnswerIndex = 2),
            Question(text = "Jakou barvu má tráva?", optionA = "Zelenou", optionB = "Modrou", optionC = "Červenou", optionD = "Žlutou", correctAnswerIndex = 0),
            Question(text = "Co je Android?", optionA = "Robot", optionB = "Operační systém", optionC = "Hra", optionD = "Jídlo", correctAnswerIndex = 1),
            Question(text = "Kolik nohou má pes?", optionA = "2", optionB = "4", optionC = "6", optionD = "8", correctAnswerIndex = 1),
            Question(text = "Kdo napsal R.U.R.?", optionA = "Karel Čapek", optionB = "Božena Němcová", optionC = "Alois Jirásek", optionD = "Jaroslav Seifert", correctAnswerIndex = 0)
        )
        dao.insertQuestions(samples)
    }
}