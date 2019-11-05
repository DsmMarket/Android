package com.dsm.dsmmarketandroid.presentation.ui.chatList

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.dsm.domain.usecase.GetChatRoomUseCase
import com.dsm.domain.usecase.JoinRoomUseCase
import com.dsm.dsmmarketandroid.R
import com.dsm.dsmmarketandroid.presentation.base.BaseViewModel
import com.dsm.dsmmarketandroid.presentation.mapper.ChatRoomModelMapper
import com.dsm.dsmmarketandroid.presentation.model.ChatRoomModel
import com.dsm.dsmmarketandroid.presentation.util.SingleLiveEvent

class ChatListViewModel(
    private val getChatRoomUseCase: GetChatRoomUseCase,
    private val joinRoomUseCase: JoinRoomUseCase,
    private val chatRoomModelMapper: ChatRoomModelMapper
) : BaseViewModel() {

    val toastEvent = SingleLiveEvent<Int>()
    val intentChatActivityEvent = SingleLiveEvent<Bundle>()
    val hideChatRefreshEvent = SingleLiveEvent<Any>()

    val chatRoomList = MutableLiveData<List<ChatRoomModel>>()

    val showLoadingDialogEvent = SingleLiveEvent<Any>()
    val hideLoadingDialogEvent = SingleLiveEvent<Any>()

    fun getChatRoom() {
        addDisposable(
            getChatRoomUseCase.create(Unit)
                .map(chatRoomModelMapper::mapFrom)
                .subscribe({
                    chatRoomList.value = it
                    hideChatRefreshEvent.call()
                }, {
                    toastEvent.value = R.string.fail_server_error
                })
        )
    }

    fun joinRoom(roomId: Int, roomTitle: String) {
        addDisposable(
            joinRoomUseCase.create(roomId)
                .doOnSubscribe { showLoadingDialogEvent.call() }
                .doOnTerminate { hideLoadingDialogEvent.call() }
                .subscribe({
                    intentChatActivityEvent.value = Bundle().apply {
                        putInt("roomId", roomId)
                        putString("email", it)
                        putString("roomTitle", roomTitle)
                    }
                }, {
                    toastEvent.value = R.string.fail_server_error
                })
        )
    }
}