package com.example.ukol_015datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "moje_nastaveni")

class MyDataManager(private val context: Context) {

    companion object {
        val KEY_TEXT = stringPreferencesKey("ulozeny_text")
    }

    suspend fun saveText(text: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_TEXT] = text
        }
    }

    val textFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEY_TEXT] ?: "Nic tu není"
        }
}