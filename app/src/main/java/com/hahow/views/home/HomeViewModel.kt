package com.hahow.views.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hahow.useCase.ApiResult
import com.hahow.useCase.GetCourseListUseCase
import com.hahow.dataModel.Course
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * ViewModel 接收來自 UI 的輸入
 * */
interface HomeViewInput {
    /**
     * 當 UI 建立完成後，呼叫此方法取得資料
     * */
    fun onViewCreated()

    /**
     * 當 UI 下拉更新時，呼叫此方法取得資料
     * */
    fun onRefreshing()

    /**
     * 點擊 CourseCell 時，呼叫此方法
     * */
    fun onCourseCellClick(title: String)
}

/**
 * ViewModel 接收 Input 取得資料後輸出給 UI
 * */
interface HomeViewOutput {
    val courseCellStateFlow: StateFlow<List<Course>>
    val isLoadingStateFlow: StateFlow<Int>
    val homeViewStateFlow: SharedFlow<HomeViewState>
}

/**
 * 將 ViewModel 區分成 Input / Output 狀態
 * 在測試時能夠更容易 Mock 及區分 "輸入"(變因) 及 "輸出"(判斷結果是否正確)
 * */
class HomeViewModel(private val courseUseCase: GetCourseListUseCase) : ViewModel(),
    HomeViewInput, HomeViewOutput {

    private val _courseListStateFlow: MutableStateFlow<List<Course>> = MutableStateFlow(listOf())
    override val courseCellStateFlow: StateFlow<List<Course>> = _courseListStateFlow

    /**
     * 假設有多個 API 在不停情況發出，使 Loading 需要額外判斷，
     * 當 Loading 數量大於 0 時，代表 Loading 狀態為顯示
     * 當 Loading 數量小於 0 時，代表 Loading 狀態為隱藏
     * */
    private val _isLoadingStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    override val isLoadingStateFlow: StateFlow<Int> = _isLoadingStateFlow

    /**
     * 透過 SharedFlow 來傳遞 UI 狀態
     * */
    private val _homeViewStateFlow: MutableSharedFlow<HomeViewState> = MutableSharedFlow()
    override val homeViewStateFlow: SharedFlow<HomeViewState> = _homeViewStateFlow

    override fun onViewCreated() {
        getCourseList()
    }

    override fun onRefreshing() {
        _courseListStateFlow.value = listOf()
        getCourseList()
    }

    private fun getCourseList() {
        courseUseCase().onEach { result ->
            when (result) {
                is ApiResult.Loading -> {
                    _isLoadingStateFlow.value += 1
                }

                is ApiResult.Error -> {
                    _homeViewStateFlow.emit(HomeViewState.ShowDialog(result.throwable.toString()))
                }

                is ApiResult.Success -> {
                    _courseListStateFlow.value = result.result
                }

                is ApiResult.Loaded -> {
                    _isLoadingStateFlow.value -= 1
                    _homeViewStateFlow.emit(HomeViewState.Loaded)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCourseCellClick(title: String) {
        Log.d("HomeViewModel", "onProductCellClicked: $title")
        viewModelScope.launch {
            _homeViewStateFlow.emit(HomeViewState.ShowToast(title))
        }
    }
}