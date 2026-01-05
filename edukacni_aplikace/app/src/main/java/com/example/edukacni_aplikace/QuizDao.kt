package com.example.edukacni_aplikace

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface QuizDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :name LIMIT 1")
    suspend fun getUser(name: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM users ORDER BY bestScore DESC")
    suspend fun getTopPlayers(): List<User>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 5")
    suspend fun getRandomQuestions(): List<Question>
}