package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.service.ProductService
import io.reactivex.Flowable

class CompletePurchaseUseCase(private val productService: ProductService) : UseCase<Int, Unit>() {
    override fun create(data: Int): Flowable<Unit> =
        productService.completePurchase(data)

}