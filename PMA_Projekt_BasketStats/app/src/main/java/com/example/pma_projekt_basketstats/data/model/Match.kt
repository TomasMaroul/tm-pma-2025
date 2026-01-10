package com.example.pma_projekt_basketstats.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val homeFGMade: Int = 0,
    val homeFGAttempted: Int = 0,
    val awayFGMade: Int = 0,
    val awayFGAttempted: Int = 0,
    val home3PMade: Int = 0,
    val home3PAttempted: Int = 0,
    val away3PMade: Int = 0,
    val away3PAttempted: Int = 0,
    val scorers: String = ""
) {
    val homeFGPercentage: Double
        get() = if (homeFGAttempted > 0) (homeFGMade.toDouble() / homeFGAttempted) * 100 else 0.0

    val awayFGPercentage: Double
        get() = if (awayFGAttempted > 0) (awayFGMade.toDouble() / awayFGAttempted) * 100 else 0.0

    val home3PPercentage: Double
        get() = if (home3PAttempted > 0) (home3PMade.toDouble() / home3PAttempted) * 100 else 0.0

    val away3PPercentage: Double
        get() = if (away3PAttempted > 0) (away3PMade.toDouble() / away3PAttempted) * 100 else 0.0
}