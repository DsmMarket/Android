package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.error.Resource
import com.dsm.domain.service.AuthService
import io.reactivex.Flowable

class LoginUseCase(private val authService: AuthService) : UseCase<Any, Resource<Unit>>() {

    override fun create(data: Any): Flowable<Resource<Unit>> =
        authService.login(data)

}