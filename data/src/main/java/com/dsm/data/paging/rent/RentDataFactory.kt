package com.dsm.data.paging.rent

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dsm.domain.entity.Product
import com.dsm.domain.repository.RentRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class RentDataFactory(private val search: String = "", private val category: String = "") : DataSource.Factory<Int, Product>(), KoinComponent {

    private val rentRepository: RentRepository by inject()

    val mutableLiveData = MutableLiveData<RentKeyedDataSource>()

    override fun create(): DataSource<Int, Product> {
        val dataSource = RentKeyedDataSource(rentRepository, search, category)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }

}