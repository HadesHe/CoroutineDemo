package com.huami.coroutinedemo.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * @date 2022/5/6
 * @author hezhanghe@zepp.com
 * @desc
 */

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.userprefDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)
