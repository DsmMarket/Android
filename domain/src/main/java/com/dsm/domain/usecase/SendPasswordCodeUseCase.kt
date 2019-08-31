package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.repository.AccountRepository
import io.reactivex.Flowable
import retrofit2.Response

class SendPasswordCodeUseCase(private val accountRepository: AccountRepository) : UseCase<String, Response<Unit>>() {
    override fun create(data: String): Flowable<Response<Unit>> =
        accountRepository.sendPasswordCode(data)
}