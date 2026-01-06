package com.example.vanocni_aplikace

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "vanocni_data")

class ChristmasDataManager(private val context: Context) {
    companion object {
        val KEY_NAME = stringPreferencesKey("uzivatel_jmeno")
        val KEY_WISH = stringPreferencesKey("hlavni_prani")
    }

    suspend fun saveSettings(name: String, wish: String) {
        context.dataStore.edit { it[KEY_NAME] = name; it[KEY_WISH] = wish }
    }

    val userPrefsFlow: Flow<Pair<String, String>> = context.dataStore.data.map {
        Pair(it[KEY_NAME] ?: "Poutníku", it[KEY_WISH] ?: "Zatím žádné přání")
    }
}