package com.hahow.views.home

sealed class HomeViewState {
    data class ShowToast(val message: String) : HomeViewState()
    data class ShowDialog(val message: String) : HomeViewState()

    object Loaded : HomeViewState()
}