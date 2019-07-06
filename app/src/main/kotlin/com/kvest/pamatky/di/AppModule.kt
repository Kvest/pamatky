package com.kvest.pamatky.di

import com.kvest.pamatky.repository.SightsRepository
import com.kvest.pamatky.repository.SightsRepositoryImpl
import com.kvest.pamatky.ui.main.MainViewModel
import com.kvest.pamatky.ui.main.SightDetailsFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // single instance of SightsRepository
    single<SightsRepository> { SightsRepositoryImpl(get(), get()) }

    viewModel { MainViewModel(get()) }
    viewModel { (guid: String) -> SightDetailsFragmentViewModel(guid = guid,  sightsRepository = get()) }
}