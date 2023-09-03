package com.hahow.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.localCacheDataStore: DataStore<Preferences> by preferencesDataStore(name = "localCacheDataStore")

val KEY_JSON_COURSE_LIST = stringPreferencesKey("KEY_JSON_COURSE_LIST")
