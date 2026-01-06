/*
 * Copyright (c) 2026. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.engdy.spacecadetstimer.data

/**
 * Copyright (c) 2026 Andy Foulke. All rights reserved.
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.media3.common.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

data class UserPreferences(
    val numPlayers: Int,
    val playBackground: Boolean,
    val usingScience: Boolean
)

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val TAG = "UserPreferenceRepo"

    private object PreferencesKeys {
        val NUM_PLAYERS = stringPreferencesKey("num_players")
        val PLAY_BACKGROUND = stringPreferencesKey("play_background")
        val USING_SCIENCE = stringPreferencesKey("using_science")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateNumPlayers(numPlayers: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NUM_PLAYERS] = numPlayers.toString()
        }
    }

    suspend fun updatePlayBackground(playBackground: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PLAY_BACKGROUND] = playBackground.toString()
        }
    }

    suspend fun updateUsingScience(usingScience: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USING_SCIENCE] = usingScience.toString()
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val numPlayers = preferences[PreferencesKeys.NUM_PLAYERS]?.toInt() ?: 3
        val playBackground = preferences[PreferencesKeys.PLAY_BACKGROUND]?.toBoolean() ?: false
        val usingScience = preferences[PreferencesKeys.USING_SCIENCE]?.toBoolean() ?: false
        return UserPreferences(numPlayers, playBackground, usingScience)
    }
}