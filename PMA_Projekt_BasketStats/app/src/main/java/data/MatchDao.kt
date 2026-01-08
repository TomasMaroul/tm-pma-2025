package data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MatchDao {
    @Insert
    suspend fun insert(match: Match)

    @Query("SELECT * FROM matches")
    suspend fun getAllMatches(): List<Match>

    @Delete
    suspend fun delete(match: Match)
}