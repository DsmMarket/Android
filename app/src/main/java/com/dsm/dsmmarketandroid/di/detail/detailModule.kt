package com.dsm.dsmmarketandroid.di.detail

import com.dsm.data.dataSource.detail.DetailDataSource
import com.dsm.data.dataSource.detail.DetailDataSourceImpl
import com.dsm.data.mapper.PurchaseDetailMapper
import com.dsm.data.repository.DetailRepositoryImpl
import com.dsm.domain.repository.DetailRepository
import com.dsm.domain.service.DetailService
import com.dsm.domain.service.DetailServiceImpl
import com.dsm.dsmmarketandroid.presentation.ui.main.purchase.purchaseDetail.PurchaseDetailViewModel
import org.koin.dsl.module

val detailModule = module {

    factory<DetailDataSource> { DetailDataSourceImpl(get(), get()) }

    factory<DetailRepository> { DetailRepositoryImpl(get(), get()) }

    factory<DetailService> { DetailServiceImpl(get()) }

    // purchase detail
    factory { PurchaseDetailMapper() }

    factory { PurchaseDetailViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}