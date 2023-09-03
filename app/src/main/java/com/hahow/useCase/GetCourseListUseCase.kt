package com.hahow.useCase

import com.hahow.data.CourseRepository
import com.hahow.dataModel.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class GetCourseListUseCase(private val repository: CourseRepository) {

    /**
     * 啟動 flow 並且在 IO 執行緒中執行
     * 分別在 onStart / onCompletion / catch 中發送不同的狀態
     * onStart: 發送 loading 狀態
     * onCompletion: 發送 loaded 狀態，取消 loading 狀態
     * catch: 發送 error 狀態
     * */
    operator fun invoke(): Flow<ApiResult<List<Course>>> =
        flow {
            emit(ApiResult.success(repository.fetchCourseData()))
        }.flowOn(Dispatchers.IO)
            .onStart { emit(ApiResult.loading()) }
            .catch { emit(ApiResult.error(it)) }
            .onCompletion { emit(ApiResult.loaded()) }
}