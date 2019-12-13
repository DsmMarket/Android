package com.dsm.domain.usecase

import com.dsm.domain.base.UseCase
import com.dsm.domain.entity.ChatRoom
import com.dsm.domain.error.Success
import com.dsm.domain.service.ChatService
import io.reactivex.Flowable

class GetChatRoomUseCase(private val chatService: ChatService) : UseCase<Unit, Success<List<ChatRoom>>>() {
    override fun create(data: Unit): Flowable<Success<List<ChatRoom>>> =
        chatService.getChatRoom()

}