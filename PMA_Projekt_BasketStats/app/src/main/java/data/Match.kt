package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int
)