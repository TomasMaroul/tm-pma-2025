package com.example.edukacni_aplikace

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,       // Jméno
    val bestScore: Int = 0,     // Skóre
    val gamesPlayed: Int = 0    // Počet her
)