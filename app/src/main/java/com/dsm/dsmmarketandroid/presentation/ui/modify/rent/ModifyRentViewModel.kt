package com.dsm.dsmmarketandroid.presentation.ui.modify.rent

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dsm.domain.usecase.GetRentDetailUseCase
import com.dsm.domain.usecase.ModifyRentUseCase
import com.dsm.dsmmarketandroid.presentation.base.BaseViewModel
import com.dsm.dsmmarketandroid.presentation.mapper.RentDetailModelMapper
import com.dsm.dsmmarketandroid.presentation.util.SingleLiveEvent
import com.dsm.dsmmarketandroid.presentation.util.isValueBlank

class ModifyRentViewModel(
    private val getRentDetailUseCase: GetRentDetailUseCase,
    private val modifyRentUseCase: ModifyRentUseCase,
    private val rentDetailModelMapper: RentDetailModelMapper
) : BaseViewModel() {

    val title = MutableLiveData<String>()
    val price = MutableLiveData<String>()
    val photo = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val category = MutableLiveData<String>()

    val unit = MutableLiveData<String>().apply { value = "0" }

    val finishActivityEvent = SingleLiveEvent<Any>()

    val toastServerErrorEvent = SingleLiveEvent<Any>()

    private fun isBlankExist() = title.isValueBlank() || price.isValueBlank()
        || photo.value == null || content.isValueBlank() || category.isValueBlank()

    val isModifyEnable = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = !isBlankExist() }
        addSource(price) { value = !isBlankExist() }
        addSource(photo) { value = !isBlankExist() }
        addSource(content) { value = !isBlankExist() }
        addSource(category) { value = !isBlankExist() }
    }

    val startHour = MutableLiveData<String>()
    val startMinute = MutableLiveData<String>()
    val endHour = MutableLiveData<String>()
    val endMinute = MutableLiveData<String>()

    private fun getFormattedTime() = startHour.value + ":" + startMinute.value + "~" + endHour.value + ":" + endMinute.value

    val rentTime: MutableLiveData<String> = MediatorLiveData<String>().apply {
        addSource(startHour) { value = getFormattedTime() }
        addSource(startMinute) { value = getFormattedTime() }
        addSource(endHour) { value = getFormattedTime() }
        addSource(endMinute) { value = getFormattedTime() }
    }

    // TODO 차라리 모델 하나 더 만들어서 이렇게 하나 하나 value설정해주는거 말고 다른 방법 찾아보자
    fun getRentDetail(postId: Int) {
        addDisposable(
            getRentDetailUseCase.create(postId)
                .map(rentDetailModelMapper::mapFrom)
                .subscribe({
                    title.value = it.title
                    price.value = it.price.split(" ")[2].substring(0, it.price.split(" ")[2].length - 1)
                    photo.value = it.img
                    content.value = it.content
                    category.value = it.category
                    rentTime.value = it.possibleTime
                }, {
                    toastServerErrorEvent.call()
                })
        )
    }

    fun modifyRent(postId: Int) {
        addDisposable(
            modifyRentUseCase.create(
                hashMapOf(
                    "postId" to postId,
                    "title" to title.value,
                    "content" to content.value,
                    "price" to unit.value + "/" + price.value,
                    "category" to category.value,
                    "possible_time" to rentTime.value
                )
            ).subscribe({
                finishActivityEvent.call()
            }, {
                toastServerErrorEvent.call()
            })
        )
    }

    fun selectPriceUnit(unit: Int) {
        this.unit.value = unit.toString()
    }

    fun setCategory(category: String) {
        this.category.value = category
    }
}