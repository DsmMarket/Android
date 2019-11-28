package com.dsm.app.viewModel

import com.dsm.app.BaseTest
import com.dsm.domain.usecase.AddSearchHistoryUseCase
import com.dsm.domain.usecase.DeleteSearchHistoryUseCase
import com.dsm.domain.usecase.GetSearchHistoryUseCase
import com.dsm.dsmmarketandroid.presentation.ui.main.search.SearchViewModel
import com.jraska.livedata.test
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

class SearchViewModelTests : BaseTest() {

    @Mock
    private lateinit var getSearchHistoryUseCase: GetSearchHistoryUseCase

    @Mock
    private lateinit var addSearchHistoryUseCase: AddSearchHistoryUseCase

    @Mock
    private lateinit var deleteSearchHistoryUseCase: DeleteSearchHistoryUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun init() {
        viewModel = SearchViewModel(getSearchHistoryUseCase, addSearchHistoryUseCase, deleteSearchHistoryUseCase)
    }

    @Test
   fun getSearchHistorySuccess() {
        val response = listOf("CONTENT")
        `when`(getSearchHistoryUseCase.create(Unit))
            .thenReturn(Flowable.just(response))

        viewModel.getSearchHistory()

        viewModel.searchHistoryList.test().assertValue(response)
    }

    @Test
    fun deleteSearchHistoryTest() {
        `when`(deleteSearchHistoryUseCase.create("CONTENT"))
            .thenReturn(Completable.complete())

        viewModel.run {
            deleteSearchHistory("CONTENT")
        }
    }
}