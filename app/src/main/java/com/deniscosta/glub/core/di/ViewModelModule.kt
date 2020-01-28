package com.deniscosta.glub.core.di

import com.deniscosta.glub.ui.main.OfferListViewModel
import com.deniscosta.glub.ui.splash.SplashScreenViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashScreenViewModel() }
    viewModel { OfferListViewModel(get()) }
}