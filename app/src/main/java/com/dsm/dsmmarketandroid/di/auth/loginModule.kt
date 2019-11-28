package com.dsm.dsmmarketandroid.di.auth

import com.dsm.data.mapper.TokenMapper
import com.dsm.domain.usecase.LoginUseCase
import com.dsm.dsmmarketandroid.presentation.ui.auth.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    factory { TokenMapper() }

    factory { LoginUseCase(get()) }

    viewModel { LoginViewModel(get()) }
}