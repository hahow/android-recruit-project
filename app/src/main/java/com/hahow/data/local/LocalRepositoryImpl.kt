package com.hahow.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.hahow.data.dataStore.KEY_JSON_COURSE_LIST
import com.hahow.data.dataStore.localCacheDataStore
import com.hahow.dataModel.Course
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class LocalRepositoryImpl(private val context: Context) : CourseLocalRepository {

    private val gson by lazy { Gson() }

    /** 將資料儲存於本地端 */
    override suspend fun saveCourseData(courseList: List<Course>) {
        val courseJson = gson.toJson(courseList)
        context.localCacheDataStore.edit { preferences -> preferences[KEY_JSON_COURSE_LIST] = courseJson }
    }

    /** 從本地端取得資料 */
    override suspend fun fetchCourseData(): List<Course> {
        val courseJson =
            context.localCacheDataStore.data.map { preferences -> preferences[KEY_JSON_COURSE_LIST] }.first()
        return if (courseJson.isNullOrEmpty()) listOf()
        else gson.fromJson(courseJson, Array<Course>::class.java).toList()
    }
}
