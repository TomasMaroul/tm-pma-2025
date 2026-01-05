package com.example.mybaskettracker_semestralniprojekt // <--- TOTO MUSÍ SEDĚT

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class MatchRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val opponent: String,
    val score: String,
    val created: Long,
    val category: String
)