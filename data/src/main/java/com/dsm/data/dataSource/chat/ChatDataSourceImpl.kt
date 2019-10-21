package com.dsm.data.dataSource.chat

import com.dsm.data.addSchedulers
import com.dsm.data.remote.Api
import com.dsm.data.remote.entity.ChatRoomEntity
import com.dsm.data.remote.entity.ChatRoomListEntity
import io.reactivex.Flowable

class ChatDataSourceImpl(private val api: Api) : ChatDataSource {

    override fun createRoom(postId: Int, type: Int): Flowable<Map<String, String>> =
        api.createRoom(postId, type).addSchedulers()

    override fun getChatRoom(): Flowable<ChatRoomListEntity> =
        api.getChatRoom().addSchedulers()

}