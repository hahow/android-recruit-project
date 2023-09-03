package com.hahow.data.network

import com.hahow.dataModel.Course


interface CourseNetworkRepository {
    suspend fun fetchCourseData(): List<Course>
}