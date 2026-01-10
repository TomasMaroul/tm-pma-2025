package com.example.pma_projekt_basketstats.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TeamDataStore(private val context: Context) {
    private val TEAMS_KEY = stringSetPreferencesKey("teams")

    val teams: Flow<Set<String>> = context.dataStore.data.map { preferences ->
        preferences[TEAMS_KEY] ?: setOf(
            "Prague Lions", 
            "Brno Bears", 
            "Ostrava Sharks", 
            "Pilsen Bulls", 
            "Liberec Eagles", 
            "Nymburk Kings"
        )
    }

    suspend fun saveTeams(teams: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[TEAMS_KEY] = teams
        }
    }
}