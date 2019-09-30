package com.dsm.dsmmarketandroid.presentation.ui.modify.purchase

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dsm.domain.usecase.GetPurchaseDetailUseCase
import com.dsm.domain.usecase.ModifyPurchaseUseCase
import com.dsm.dsmmarketandroid.presentation.base.BaseViewModel
import com.dsm.dsmmarketandroid.presentation.mapper.PurchaseDetailModelMapper
import com.dsm.dsmmarketandroid.presentation.util.ListLiveData
import com.dsm.dsmmarketandroid.presentation.util.SingleLiveEvent

class ModifyPurchaseViewModel(
    private val getPurchaseDetailUseCase: GetPurchaseDetailUseCase,
    private val modifyPurchaseUseCase: ModifyPurchaseUseCase,
    private val purchaseDetailModelMapper: PurchaseDetailModelMapper
) : BaseViewModel() {

    val title = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val category = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val imageList = ListLiveData<String>()

    val finishActivityEvent = SingleLiveEvent<Any>()

    val toastServerErrorEvent = SingleLiveEvent<Any>()

    private fun MutableLiveData<String>.isValueBlank() = this.value.isNullOrBlank()

    private fun isBlankExist() = title.isValueBlank() || price.isValueBlank()
        || content.isValueBlank() || category.isValueBlank()

    val isModifyEnable = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = !isBlankExist() }
        addSource(price) { value = !isBlankExist() }
        addSource(content) { value = !isBlankExist() }
        addSource(category) { value = !isBlankExist() }
    }

    fun getPurchaseDetail(postId: Int) {
        addDisposable(
            getPurchaseDetailUseCase.create(postId)
                .map(purchaseDetailModelMapper::mapFrom)
                .subscribe({
                    title.value = it.title
                    price.value = it.price.substring(0, it.price.length - 1)
                    category.value = it.category
                    content.value = it.content
                    imageList.value = it.img as ArrayList<String>
                }, {
                    toastServerErrorEvent.call()
                })
        )
    }

    fun modifyPurchase(postId: Int) {
        addDisposable(
            modifyPurchaseUseCase.create(
                hashMapOf(
                    "postId" to postId,
                    "title" to title.value,
                    "content" to content.value,
                    "price" to price.value,
                    "category" to category.value
                )
            ).subscribe({
                finishActivityEvent.call()
            }, {
                toastServerErrorEvent.call()
            })
        )
    }
}