package com.example.mybaskettracker_semestralniprojekt // <--- TOTO MUSÍ SEDĚT

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MatchDao {
    @Insert
    suspend fun insert(match: MatchRecord)

    @Query("SELECT * FROM matches ORDER BY created DESC")
    suspend fun getAllMatches(): List<MatchRecord>

    @Update
    suspend fun update(match: MatchRecord)

    @Delete
    suspend fun delete(match: MatchRecord)

    @Query("SELECT * FROM matches WHERE opponent LIKE '%' || :searchQuery || '%'")
    suspend fun searchMatches(searchQuery: String): List<MatchRecord>
}