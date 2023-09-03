package com.hahow.data

import com.hahow.dataModel.Course

interface CourseRepository {
    suspend fun fetchCourseData(): List<Course>
}