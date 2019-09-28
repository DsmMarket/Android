package com.dsm.dsmmarketandroid.presentation.ui.addComment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.dsm.domain.usecase.PostCommentUseCase
import com.dsm.dsmmarketandroid.presentation.base.BaseViewModel
import com.dsm.dsmmarketandroid.presentation.util.SingleLiveEvent

class AddCommentViewModel(private val postCommentUseCase: PostCommentUseCase) : BaseViewModel() {

    val content = MutableLiveData<String>()

    val isAddCommentEnable: LiveData<Boolean> = Transformations.map(content) { it != "" }

    val finishActivityEvent = SingleLiveEvent<Any>()
    val toastServerErrorEvent = SingleLiveEvent<Any>()

    fun postComment(postId: Int, type: Int) {
        addDisposable(
            postCommentUseCase.create(
                hashMapOf(
                    "postId" to postId,
                    "content" to content.value,
                    "type" to type
                )
            ).subscribe({
                finishActivityEvent.call()
            }, {
                toastServerErrorEvent.call()
            })
        )
    }
}