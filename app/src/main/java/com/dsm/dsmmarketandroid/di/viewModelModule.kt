package com.dsm.dsmmarketandroid.di

import com.dsm.dsmmarketandroid.presentation.ui.login.LoginViewModel
import com.dsm.dsmmarketandroid.presentation.ui.password.changePassword.ChangePasswordViewModel
import com.dsm.dsmmarketandroid.presentation.ui.password.passwordCodeConfirm.PasswordCodeConfirmViewModel
import com.dsm.dsmmarketandroid.presentation.ui.password.sendPasswordCode.SendPasswordCodeViewModel
import com.dsm.dsmmarketandroid.presentation.ui.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }

    viewModel { SignUpViewModel(get()) }

    viewModel { SendPasswordCodeViewModel(get()) }

    viewModel { PasswordCodeConfirmViewModel(get()) }

    viewModel { ChangePasswordViewModel(get()) }
}