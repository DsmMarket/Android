package com.dsm.app.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dsm.domain.entity.PostCategory
import com.dsm.domain.usecase.GetPostCategoryUseCase
import com.dsm.dsmmarketandroid.presentation.mapper.PostCategoryModelMapper
import com.dsm.dsmmarketandroid.presentation.ui.postCategory.PostCategoryViewModel
import com.jraska.livedata.test
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PostCategoryViewModelTests {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getPostCategoryUseCase: GetPostCategoryUseCase

    private val postCategoryModelMapper = PostCategoryModelMapper()

    private lateinit var viewModel: PostCategoryViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        viewModel = PostCategoryViewModel(getPostCategoryUseCase, postCategoryModelMapper)
    }

    @Test
    fun getPostCategorySuccess() {
        val response = arrayListOf(
            PostCategory(parent = "PARENT", child = listOf("child", "child"))
        )
        `when`(getPostCategoryUseCase.create(Unit))
            .thenReturn(Flowable.just(response))

        viewModel.getPostCategory()

        val afterMap = postCategoryModelMapper.mapFrom(response)

        viewModel.categoryList.test().assertValue { it.containsAll(afterMap) }
    }
}