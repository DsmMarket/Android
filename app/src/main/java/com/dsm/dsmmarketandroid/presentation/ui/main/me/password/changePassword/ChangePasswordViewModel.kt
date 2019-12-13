package com.dsm.dsmmarketandroid.presentation.ui.main.me.password.changePassword

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dsm.data.error.exception.UnauthorizedException
import com.dsm.domain.usecase.ChangePasswordUseCase
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.base.BaseViewModel
import com.dsm.dsmmarketandroid.presentation.util.SingleLiveEvent
import com.dsm.dsmmarketandroid.presentation.util.isValueBlank

class ChangePasswordViewModel(private val changePasswordUseCase: ChangePasswordUseCase) : BaseViewModel() {

    val newPassword = MutableLiveData<String>()
    val reType = MutableLiveData<String>()

    val isChangePasswordEnable = MediatorLiveData<Boolean>().apply {
        addSource(newPassword) { value = !newPassword.isValueBlank() && !reType.isValueBlank() }
        addSource(reType) { value = !newPassword.isValueBlank() && !reType.isValueBlank() }
        value = false
    }

    val finishActivityEvent = SingleLiveEvent<Any>()
    val toastEvent = SingleLiveEvent<Int>()
    val changePasswordLogEvent = SingleLiveEvent<Any>()

    fun changePassword() {
        if (newPassword.value != reType.value) {
            toastEvent.value = R.string.fail_diff_password
            return
        }

        addDisposable(
            changePasswordUseCase.create(newPassword.value!!)
                .doOnComplete { changePasswordLogEvent.call() }
                .subscribe({
                    finishActivityEvent.call()
                }, {
                    toastEvent.value = when (it) {
                        is UnauthorizedException -> R.string.fail_unauthorized
                        else -> R.string.fail_server_error
                    }
                })
        )
    }
}