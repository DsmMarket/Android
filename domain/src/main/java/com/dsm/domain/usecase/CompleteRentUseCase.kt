package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.error.Resource
import com.dsm.domain.service.ProductService
import io.reactivex.Flowable

class CompleteRentUseCase(private val productService: ProductService) : UseCase<Int, Resource<Unit>>() {
    override fun create(data: Int): Flowable<Resource<Unit>> =
        productService.completeRent(data)

}