package com.dsm.app.viewModel

import com.dsm.app.BaseTest
import com.dsm.app.createHttpException
import com.dsm.domain.entity.Recommend
import com.dsm.domain.entity.RentDetail
import com.dsm.domain.usecase.*
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.mapper.RecommendModelMapper
import com.dsm.dsmmarketandroid.presentation.mapper.RentDetailModelMapper
import com.dsm.dsmmarketandroid.presentation.ui.rentDetail.RentDetailViewModel
import com.dsm.dsmmarketandroid.presentation.util.ProductType
import com.jraska.livedata.test
import io.reactivex.Flowable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class RentDetailViewModelTests : BaseTest() {

    @Mock
    private lateinit var getRentDetailUseCase: GetRentDetailUseCase

    @Mock
    private lateinit var interestUseCase: InterestUseCase

    @Mock
    private lateinit var getRelatedUseCase: GetRelatedUseCase

    @Mock
    private lateinit var unInterestUseCase: UnInterestUseCase

    @Mock
    private lateinit var createRoomUseCase: CreateRoomUseCase

    private val rentDetailModelMapper = RentDetailModelMapper()
    private val recommendModelMapper = RecommendModelMapper()

    private lateinit var viewModel: RentDetailViewModel

    @Before
    fun init() {
        viewModel = RentDetailViewModel(getRentDetailUseCase, interestUseCase, unInterestUseCase, getRelatedUseCase, createRoomUseCase, recommendModelMapper, rentDetailModelMapper)
    }

    @Test
    fun interestRentSuccess() {
        viewModel.isInterest.value = false

        `when`(interestUseCase.create(InterestUseCase.Params(0, ProductType.RENT)))
            .thenReturn(Flowable.just(Unit))

        viewModel.onClickInterest(0)

        assertTrue(viewModel.isInterest.test().value())
        viewModel.toastEvent.test().assertValue(R.string.interest)
    }

    @Test
    fun `interest rent failed server error test`() {
        viewModel.run {
            isInterest.value = false

            `when`(interestUseCase.create(InterestUseCase.Params(0, ProductType.RENT)))
                .thenReturn(Flowable.error(Exception()))

            onClickInterest(0)

            toastEvent.test().assertValue(R.string.fail_server_error)
        }
    }

    @Test
    fun unInterestRentSuccess() {
        viewModel.isInterest.value = true

        `when`(unInterestUseCase.create(UnInterestUseCase.Params(0, 1)))
            .thenReturn(Flowable.just(Unit))

        viewModel.onClickInterest(0)

        assertFalse(viewModel.isInterest.test().value())
        viewModel.toastEvent.test().assertValue(R.string.un_interest)
    }

    @Test
    fun `un interest rent failed server error`() {
        viewModel.run {
            isInterest.value = true

            `when`(unInterestUseCase.create(UnInterestUseCase.Params(0, ProductType.RENT)))
                .thenReturn(Flowable.error(Exception()))

            onClickInterest(0)

            viewModel.toastEvent.test().assertValue(R.string.fail_server_error)
        }
    }

    @Test
    fun getRelatedProductSuccess() {
        val response = listOf(
            Recommend(0, "TITLE", "IMG")
        )

        `when`(getRelatedUseCase.create(GetRelatedUseCase.Params(0, 1)))
            .thenReturn(Flowable.just(response))

        viewModel.getRelatedProduct(0)

        viewModel.relatedList.test().assertValue(recommendModelMapper.mapFrom(response))
    }

    @Test
    fun `get rent detail success test`() {
        val response = RentDetail("", "", 0, "", "", "", "", "", 0, true, "")
        `when`(getRentDetailUseCase.create(0))
            .thenReturn(Flowable.just(response))

        viewModel.run {
            getRentDetail(0)

            val mapped = rentDetailModelMapper.mapFrom(response)

            isInterest.test().assertValue(mapped.isInterest)
            rentDetail.test().assertValue(mapped)
        }
    }

    @Test
    fun `get rent detail failed not exist post test`() {
        `when`(getRentDetailUseCase.create(0))
            .thenReturn(Flowable.error(createHttpException(410)))

        viewModel.run {
            getRentDetail(0)

            toastEvent.test().assertValue(R.string.fail_non_exist_post)
        }
    }

    @Test
    fun `get rent detail failed server error test`() {
        `when`(getRentDetailUseCase.create(0))
            .thenReturn(Flowable.error(Exception()))

        viewModel.run {
            getRentDetail(0)

            toastEvent.test().assertValue(R.string.fail_server_error)
        }
    }
}