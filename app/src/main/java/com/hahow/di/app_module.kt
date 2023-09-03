package com.hahow.di

import com.hahow.data.CourseRepository
import com.hahow.data.RepositoryImpl
import com.hahow.data.local.CourseLocalRepository
import com.hahow.useCase.GetCourseListUseCase
import com.hahow.data.local.LocalRepositoryImpl
import com.hahow.data.network.CourseNetworkRepository
import com.hahow.data.network.NetworkRepositoryImpl
import com.hahow.views.home.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<CourseNetworkRepository> { NetworkRepositoryImpl(androidContext()) }
    factory<CourseLocalRepository> { LocalRepositoryImpl(androidContext()) }
    factory<CourseRepository> { RepositoryImpl(androidContext(), get(), get()) }
    factory { GetCourseListUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}