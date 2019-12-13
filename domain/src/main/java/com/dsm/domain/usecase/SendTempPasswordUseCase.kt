package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.service.AccountService
import io.reactivex.Flowable

class SendTempPasswordUseCase(private val accountService: AccountService) : UseCase<String, Unit>() {
    override fun create(data: String): Flowable<Unit> =
        accountService.sendTempPassword(data)

}