package com.dsm.app.viewModel

import com.dsm.app.BaseTest
import com.dsm.domain.entity.Product
import com.dsm.domain.usecase.GetInterestUseCase
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.mapper.ProductModelMapper
import com.dsm.dsmmarketandroid.presentation.model.ProductModel
import com.dsm.dsmmarketandroid.presentation.ui.main.me.interest.InterestViewModel
import com.dsm.dsmmarketandroid.presentation.util.ProductType
import com.jraska.livedata.test
import io.reactivex.Flowable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class InterestViewModelTests : BaseTest() {

    @Mock
    private lateinit var getInterestUseCase: GetInterestUseCase

    private val productModelMapper = ProductModelMapper()

    private lateinit var viewModel: InterestViewModel

    @Before
    fun init() {
        viewModel = InterestViewModel(getInterestUseCase, productModelMapper)
    }

    @Test
    fun `isPurchaseEmpty false test`() {
        viewModel.purchaseList.value = listOf(ProductModel(0, "TITLE", "IMG", "CREATED_AT", "PRICE"))

        assertFalse(viewModel.isPurchaseEmpty.test().value())
    }

    @Test
    fun `isPurchaseEmpty true test`() {
        viewModel.purchaseList.value = listOf()

        assertTrue(viewModel.isPurchaseEmpty.test().value())
    }

    @Test
    fun `isRentEmpty false test`() {
        viewModel.rentList.value = listOf(ProductModel(0, "TITLE", "IMG", "CREATED_AT", "PRICE"))

        assertFalse(viewModel.isRentEmpty.test().value())
    }

    @Test
    fun `isRentEmpty true test`() {
        viewModel.rentList.value = listOf()

        assertTrue(viewModel.isRentEmpty.test().value())
    }

    @Test
    fun `get interest purchase success test`() {
        val response = listOf(Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"))
        `when`(getInterestUseCase.create(0))
            .thenReturn(Flowable.just(response))

        viewModel.getInterest(ProductType.PURCHASE)

        viewModel.purchaseList.test().assertValue(productModelMapper.mapFrom(response))
    }

    @Test
    fun `get interest purchase failed test`() {
        `when`(getInterestUseCase.create(ProductType.PURCHASE))
            .thenReturn(Flowable.error(Exception()))

        viewModel.getInterest(ProductType.PURCHASE)

        viewModel.toastEvent.test().assertValue(R.string.fail_server_error)
    }

    @Test
    fun getInterestRent() {
        val response = listOf(
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"),
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"),
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"),
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"),
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"),
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE"),
            Product(0, "TITLE", "IMG", "CREATED_AT", "PRICE")
        )
        `when`(getInterestUseCase.create(1))
            .thenReturn(Flowable.just(response))

        viewModel.getInterest(ProductType.RENT)

        viewModel.rentList.test().assertValue(productModelMapper.mapFrom(response))
    }

     @Test
     fun `get interest rent failed test`() {
         `when`(getInterestUseCase.create(ProductType.RENT))
             .thenReturn(Flowable.error(Exception()))

         viewModel.getInterest(ProductType.RENT)

         viewModel.toastEvent.test().assertValue(R.string.fail_server_error)
     }
}