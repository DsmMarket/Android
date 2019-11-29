package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.entity.Product
import com.dsm.domain.service.RentService
import io.reactivex.Flowable

class GetInterestRentUseCase(private val rentService: RentService) : UseCase<Unit, List<Product>>() {
    override fun create(data: Unit): Flowable<List<Product>> =
        rentService.getInterestRent()

}