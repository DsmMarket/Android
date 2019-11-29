package com.dsm.data.repository

import com.dsm.data.dataSource.purchase.PurchaseDataSource
import com.dsm.data.mapper.InterestProductMapper
import com.dsm.data.mapper.ProductMapper
import com.dsm.domain.entity.Product
import com.dsm.domain.repository.PurchaseRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class PurchaseRepositoryImpl(
    private val dataSource: PurchaseDataSource,
    private val productMapper: ProductMapper,
    private val interestProductMapper: InterestProductMapper
) : PurchaseRepository {

    override fun getRemotePurchaseList(page: Int, pageSize: Int, search: String, category: String): Flowable<List<Product>> =
        dataSource.getRemotePurchaseList(page, pageSize, search, category).map(productMapper::mapFrom)

    override fun getLocalPurchaseList(page: Int, pageSize: Int): List<Product> =
        dataSource.getLocalPurchaseList(page, pageSize).map(productMapper::mapFrom)

    override fun addLocalPurchaseList(list: List<Product>): Completable =
        dataSource.addLocalPurchaseList(productMapper.mapFrom(list, 0))

    override fun getRemoteInterestPurchase(): Flowable<List<Product>> =
        dataSource.getRemoteInterestPurchase().map(interestProductMapper::mapFrom)

    override fun getLocalInterestPurchase(): List<Product> =
        dataSource.getLocalInterestPurchase().map { interestProductMapper.mapFrom(it) }

    override fun addLocalInterestPurchase(interestPurchase: List<Product>): Completable =
        dataSource.addLocalInterestPurchase(interestProductMapper.mapFrom(interestPurchase, 0))

}