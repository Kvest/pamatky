package com.kvest.pamatky.di

import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.repository.SightsRepositoryImpl
import com.kvest.pamatky.ui.TestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // single instance of SightsRepository
    single<SightsRepository> { SightsRepositoryImpl(get()) }

    viewModel { TestViewModel(get()) }
}