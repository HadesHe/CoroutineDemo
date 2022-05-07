package com.huami.coroutinedemo.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @date 2022/5/7
 * @author hezhanghe@zepp.com
 * @desc
 */
class DataStoreRepo(private val dataStore: DataStore<Preferences>) {

    private val TAG = "DataStoreRepo"

    private object PreferencesKeys {
        val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
    }

    val counterPreferencesFlow: Flow<Int> = dataStore.data.map {
        it[PreferencesKeys.EXAMPLE_COUNTER] ?: 0
    }

    suspend fun incrementCounter() {
        dataStore.edit {
            val currentCounterValue = it[PreferencesKeys.EXAMPLE_COUNTER] ?: 0
            it[PreferencesKeys.EXAMPLE_COUNTER] = currentCounterValue + 1
        }
    }

}