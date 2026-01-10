package com.example.pma_projekt_basketstats.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pma_projekt_basketstats.data.model.Match

@Dao
interface MatchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Match)

    @Update
    suspend fun update(match: Match)

    @Query("SELECT * FROM matches ORDER BY id DESC")
    suspend fun getAllMatches(): List<Match>

    @Query("SELECT * FROM matches WHERE id = :id")
    suspend fun getMatchById(id: Int): Match?

    @Delete
    suspend fun delete(match: Match)
}