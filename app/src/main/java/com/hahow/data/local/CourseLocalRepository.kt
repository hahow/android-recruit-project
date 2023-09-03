package com.hahow.data.local

import com.hahow.dataModel.Course

interface CourseLocalRepository {

    suspend fun saveCourseData(courseList: List<Course>)
    suspend fun fetchCourseData(): List<Course>
}