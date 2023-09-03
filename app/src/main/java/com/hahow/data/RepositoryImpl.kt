package com.hahow.data

import android.content.Context
import com.hahow.data.local.CourseLocalRepository
import com.hahow.data.network.CourseNetworkRepository
import com.hahow.dataModel.Course
import com.hahow.extensions.isNetworkConnected

class RepositoryImpl(
    private val context: Context,
    private val localRepository: CourseLocalRepository,
    private val networkRepository: CourseNetworkRepository
) : CourseRepository {

    /**
     * 判斷是否有網路
     * (1) 有網路則從網路取得資料，並儲存於本地端
     * (2) 無網路則從本地端取得資料(dataStore)
     * */
    override suspend fun fetchCourseData(): List<Course> {
        if (context.isNetworkConnected()) {
            val courseList = networkRepository.fetchCourseData()
            localRepository.saveCourseData(courseList)
            return courseList
        } else {
            return localRepository.fetchCourseData()
        }
    }
}