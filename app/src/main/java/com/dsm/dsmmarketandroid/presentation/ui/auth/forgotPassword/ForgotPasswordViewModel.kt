package com.dsm.dsmmarketandroid.presentation.ui.auth.forgotPassword

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.dsm.domain.error.Resource
import com.dsm.domain.usecase.SendTempPasswordUseCase
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.base.BaseViewModel
import com.dsm.dsmmarketandroid.presentation.util.Analytics
import com.dsm.dsmmarketandroid.presentation.util.SingleLiveEvent
import com.dsm.dsmmarketandroid.presentation.util.Validator

class ForgotPasswordViewModel(private val sendTempPasswordUseCase: SendTempPasswordUseCase) : BaseViewModel() {

    val email = MutableLiveData<String>().apply { value = "" }

    val isSendTempPasswordEnable: LiveData<Boolean> = Transformations.map(email) { it != "" }

    val showLoadingDialogEvent = SingleLiveEvent<Any>()
    val hideLoadingDialogEvent = SingleLiveEvent<Any>()
    val finishActivityEvent = SingleLiveEvent<Any>()
    val toastEvent = SingleLiveEvent<Int>()
    val sendTempPasswordLogEvent = SingleLiveEvent<Bundle>()

    fun sendTempPassword() {
        if (!Validator.validEmail(email.value!!)) {
            toastEvent.value = R.string.fail_invalid_email
            return
        }

        addDisposable(
            sendTempPasswordUseCase.create(email.value!!)
                .doOnSubscribe { showLoadingDialogEvent.call() }
                .doOnTerminate { hideLoadingDialogEvent.call() }
                .doOnComplete { sendTempPasswordLogEvent.value = Bundle().apply { putString(Analytics.USER_EMAIL, email.value) } }
                .subscribe({
                    when (it) {
                        is Resource.Success -> {
                            toastEvent.value = R.string.send_temp_password_success
                            finishActivityEvent.call()
                        }
                        is Resource.Error -> toastEvent.value = R.string.fail_server_error
                    }
                }, {})
        )
    }
}